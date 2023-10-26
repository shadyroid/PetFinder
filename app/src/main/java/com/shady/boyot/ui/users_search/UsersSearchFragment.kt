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
import androidx.navigation.findNavController
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.adapters.BuildingsAdapter
import com.shady.boyot.classes.dialogs.BuildingsDialog
import com.shady.boyot.classes.utils.Validator
import com.shady.boyot.databinding.FragmentUsersSearchBinding
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.responses.BuildingsResponse
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
    lateinit var buildingsDialog: BuildingsDialog

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
        buildingsDialog.setListener(object : BuildingsAdapter.Listener {
            override fun onBuildingClick(building: BuildingBean?) {
                buildingsDialog.dismiss()
                building?.let {
                    selectedBuildingId = it.id
                    binding.btnBuildings.text = it.name
                }

            }
        })
        binding.toolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack()
        }
        binding.btnSearch.setOnClickListener { onSearchClick() }
        binding.btnBuildings.setOnClickListener { buildingsDialog.show() }

        binding.rgOptions.setOnCheckedChangeListener { radioGroup: RadioGroup, id: Int ->
            binding.btnBuildings.visibility = GONE
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
                    binding.etSearch.setHint(R.string.building_number)
                    searchType = "building_number"
                }
            }

        }

    }


    private fun initArguments() {

    }

    private fun onBuildingsResponse(response: BuildingsResponse) {
        response.data?.data.let { buildingsDialog.setData(it) }

    }

    private fun initObserves() {
        lifecycleScope.launch {
            viewModel.buildingsResponseMutableStateFlow.collect {
                if (it != null) onBuildingsResponse(it)
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
            "building_number" -> buildingId = selectedBuildingId
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


    private fun isValid(): Boolean =
        validator.isNotNull(clientCode, R.string.client_code_is_required)||
        validator.isNotNull(clientName, R.string.client_name_is_required)||
        validator.isNotNull(contractNumber, R.string.contract_number_is_required)||
        validator.isNotNull(buildingId, R.string.building_id_is_required)||
        validator.isNotNull(unitId, R.string.unit_id_is_required)

}