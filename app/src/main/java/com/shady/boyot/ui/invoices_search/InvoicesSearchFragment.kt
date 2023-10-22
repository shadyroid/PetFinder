package com.shady.boyot.ui.invoices_search

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
import com.shady.boyot.classes.adapters.BuildingsAdapter
import com.shady.boyot.classes.dialogs.BuildingsDialog
import com.shady.boyot.classes.utils.Validator
import com.shady.boyot.databinding.FragmentInvoicesSearchBinding
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.responses.BuildingsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class InvoicesSearchFragment : BaseFragment() {
    private lateinit var binding: FragmentInvoicesSearchBinding
    private val viewModel: InvoicesSearchViewModel by viewModels()

    @Inject
    lateinit var validator: Validator
    var searchType: String = "user_name"
    var buildingId: String = "5"

    @Inject
    lateinit var buildingsDialog: BuildingsDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentInvoicesSearchBinding.inflate(inflater, container, false)
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
                    buildingId = it.id
                    binding.btnBuildings.text = it.name
                }

            }
        })
        binding.btnSearch.setOnClickListener { onSearchClick() }
        binding.btnBuildings.setOnClickListener { buildingsDialog.show() }

        binding.rgOptions.setOnCheckedChangeListener { radioGroup: RadioGroup, id: Int ->
            binding.btnBuildings.visibility = GONE
            when (id) {
                R.id.rb_user_name -> {
                    binding.etSearch.setHint(R.string.user_name)
                    searchType = "user_name"
                }

                R.id.rb_user_code -> {
                    binding.etSearch.setHint(R.string.user_code)
                    searchType = "user_code"
                }

                R.id.rb_contract_number -> {
                    binding.etSearch.setHint(R.string.contract_number)
                    searchType = "contract_number"
                }

                R.id.rb_building_number -> {
                    binding.btnBuildings.visibility = VISIBLE
                    binding.etSearch.setHint(R.string.building_number)
                    searchType = "building_number"
                }
            }

        }

    }


    private fun initArguments() {
        lifecycleScope.launch {
            viewModel.buildingsResponseMutableStateFlow.collect {
                if (it != null) onBuildingsResponse(it)
            }
        }

    }

    private fun onBuildingsResponse(response: BuildingsResponse) {
        buildingsDialog.setData(response.data)

    }

    private fun initObserves() {

    }

    private fun onSearchClick() {
        navigate(
            InvoicesSearchFragmentDirections.actionNavInvoicesSearchToNavUsers(
                searchType,
                binding.etSearch.text.toString(),
                buildingId
            )
        )
    }


    private fun isValid(): Boolean =
        validator.isNotEmpty(binding.etSearch, R.string.required)
}