package com.shady.boyot.ui.users_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.adapters.OptionsAdapter
import com.shady.boyot.classes.dialogs.OptionsDialog
import com.shady.boyot.classes.utils.Validator
import com.shady.boyot.databinding.FragmentUsersSearchBinding
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.beans.IdNameBean
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.UnitsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class UsersSearchFragment : BaseFragment() {
    private lateinit var binding: FragmentUsersSearchBinding
    private val viewModel: UsersSearchViewModel by viewModels()

    @Inject
    lateinit var validator: Validator
    private var selectedBuildingId: String? = null
    private var selectedUnitId: String? = null


    var searchType: String = "user_name"
    private var clientCode: String? = null
    private var clientName: String? = null
    private var contractNumber: String? = null
    private var buildingId: String? = null
    private var unitId: String? = null


    @Inject
    lateinit var buildingsDialog: OptionsDialog
    @Inject
    lateinit var unitsDialog: OptionsDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentUsersSearchBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        initArguments()
        initObserves()
        viewModel.requestBuildings()
        buildingsDialog.setListener(object : OptionsAdapter.Listener {
            override fun onBuildingClick(idNameBean: IdNameBean?) {
                buildingsDialog.dismiss()
                idNameBean?.let {
                    selectedBuildingId = it.id
                    binding.btnBuildings.text = it.name
                    selectedUnitId = null;
                    binding.btnUnits.text = ""
                    requestUnits();
                }

            }
        })
  unitsDialog.setListener(object : OptionsAdapter.Listener {
            override fun onBuildingClick(idNameBean: IdNameBean?) {
                unitsDialog.dismiss()
                idNameBean?.let {
                    selectedUnitId = it.id
                    binding.btnUnits.text = it.name
                }

            }
        })
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.btnSearch.setOnClickListener { onSearchClick() }
        binding.btnBuildings.setOnClickListener { buildingsDialog.show() }
        binding.btnUnits.setOnClickListener { unitsDialog.show() }

        binding.rgOptions.setOnCheckedChangeListener { radioGroup: RadioGroup, id: Int ->
            binding.btnBuildings.visibility = GONE
            binding.btnUnits.visibility = GONE
            binding.cvSearch.visibility = VISIBLE
            selectedBuildingId = null;
            selectedUnitId = null;
            when (id) {
                R.id.rb_user_name -> {
                    binding.etSearch.setText("")
                    binding.etSearch.setHint(R.string.user_name)
                    searchType = "user_name"
                }

                R.id.rb_user_code -> {
                    binding.etSearch.setText("")
                    binding.etSearch.setHint(R.string.user_code)
                    searchType = "user_code"
                }

                R.id.rb_contract_number -> {
                    binding.etSearch.setText("")
                    binding.etSearch.setHint(R.string.contract_number)
                    searchType = "contract_number"
                }

                R.id.rb_building_number -> {
                    binding.etSearch.setText("")
                    binding.btnBuildings.visibility = VISIBLE
                    binding.btnUnits.visibility = VISIBLE
                    binding.cvSearch.visibility = GONE
                    searchType = "building_number"
                }
            }

        }

    }

    fun requestUnits() {
        val map = HashMap<String, String>()
        map["building_id"] = selectedBuildingId.toString();
        viewModel.requestUnits(map)
    }
    private fun initArguments() {

    }

    private fun onBuildingsResponse(response: BuildingsResponse) {
        response.data?.data.let { buildingsDialog.setData(it) }

    }
    private fun onUnitsResponse(response: UnitsResponse) {
        response.data?.data.let { unitsDialog.setData(it) }

    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.buildingsResponseMutableStateFlow.collect {
                if (it != null) onBuildingsResponse(it)
            }
        }

     lifecycleScope.launch {
            viewModel.unitsResponseMutableStateFlow.collect {
                if (it != null) onUnitsResponse(it)
            }
        }



    }

    private fun onSearchClick() {

        clientCode = null
        clientName = null
        contractNumber = null
        buildingId = null
        unitId = null
        when (searchType) {
            "user_name" -> clientName = binding.etSearch.text.toString()
            "user_code" -> clientCode = binding.etSearch.text.toString()
            "contract_number" -> contractNumber = binding.etSearch.text.toString()
            "building_number" -> {
                buildingId = selectedBuildingId
                unitId = selectedUnitId
            }
        }
        if (!isValid())
            return

        navigate(
            UsersSearchFragmentDirections.actionNavUsersSearchToNavUsers(
                clientCode,
                clientName,
                contractNumber,
                buildingId,
                unitId,
            )
        )
    }


    private fun isValid(): Boolean {
        if (clientCode.isNullOrEmpty()&& clientName.isNullOrEmpty() && contractNumber.isNullOrEmpty() && buildingId.isNullOrEmpty() && unitId.isNullOrEmpty()) {
            appToast.showMessage(
                requireContext().getString(
                    when (searchType) {
                        "user_name" -> R.string.client_name_is_required
                        "user_code" -> R.string.client_code_is_required
                        "contract_number" -> R.string.contract_number_is_required
                        "building_number" -> R.string.building_id_and_unit_id_is_required
                        else -> R.string.required
                    }

                )
            )
            return false
        }
        return true

    }

}