package com.softxpert.petfinder.ui.pets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.softxpert.data.preferenceses.PreferencesHelper
import com.softxpert.domain.entity.beans.PetBean
import com.softxpert.domain.entity.responses.AuthResponse
import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import com.softxpert.petfinder.classes.adapters.PetsAdapter
import com.softxpert.petfinder.classes.adapters.TypesAdapter
import com.softxpert.petfinder.classes.utils.EndlessRecyclerViewScrollListener
import com.softxpert.petfinder.databinding.FragmentPetsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PetsFragment : Fragment(), TypesAdapter.Listener, PetsAdapter.Listener,
    AppBarLayout.OnOffsetChangedListener {
    private var _binding: FragmentPetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PetsViewModel by viewModels()

    private var selectedType: String = ""

    @Inject
    lateinit var typesAdapter: TypesAdapter

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var currentPage = 1

    @Inject
    lateinit var petsAdapter: PetsAdapter
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            resetState()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        initTypesAdapter()
        initPetsAdapter()
        if (!viewModel.isFragmentInitializedBefore) {
            viewModel.isFragmentInitializedBefore = true
            initObserves()
            requestTypes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initTypesAdapter() {
        typesAdapter.listener = this
        binding.rvTypes.adapter = typesAdapter
    }

    private fun initPetsAdapter() {
        petsAdapter.listener = this
        binding.rvPets.adapter = petsAdapter
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(currentPage) {
            override fun onLoadMore(page: Int) {
                currentPage = page
                requestPets()
            }
        }
        binding.rvPets.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            viewModel.typesResponseMutableStateFlow.collect {
                if (it != null) onTypesResponse(it)
            }
        }
        lifecycleScope.launch {
            viewModel.petsResponseMutableStateFlow.collect {
                if (it != null) onPetsResponse(it)
            }
        }
        lifecycleScope.launch {
            viewModel.authResponseMutableStateFlow.collect {
                if (it != null) onAuthResponse(it)
            }
        }

    }

    private fun onAuthResponse(response: AuthResponse) {
        preferencesHelper.putAuthToken(response.access_token)
        requestPets()
    }

    private fun requestTypes() {
        viewModel.requestTypes()
    }

    fun requestPets() {
        val map = HashMap<String, String>()
        map["page"] = currentPage.toString()
        if (selectedType != "All")
            map["type"] = selectedType
        viewModel.requestPets(map)
    }


    private fun onTypesResponse(response: TypesResponse) {
        typesAdapter.setData(
            response.types
        )
        onTypeClick(response.types[0].name)
    }

    private fun onPetsResponse(response: PetsResponse) {
        if (currentPage == 1) {
            binding.shimmer.visibility = View.GONE
        }
        petsAdapter.setFinishedLoading(response.animals.size < 5)
        petsAdapter.addData(response.animals)
    }

    override fun onPetClick(pet: PetBean) {
        binding.root.findNavController()
            .navigate(PetsFragmentDirections.actionNavPetsToNavPetDetails(pet))
    }

    override fun onTypeClick(type: String) {
        selectedType = type
        resetState()
    }

    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        binding.swipeRefreshLayout.isEnabled = verticalOffset == 0
    }

    override fun onPause() {
        super.onPause()
        binding.appBar.removeOnOffsetChangedListener(this)
    }

    private fun resetState() {
        viewModel.clear()
        petsAdapter.clear()
        endlessRecyclerViewScrollListener.resetState()
        binding.shimmer.visibility = View.VISIBLE
        currentPage = 1
        requestPets()
    }
}