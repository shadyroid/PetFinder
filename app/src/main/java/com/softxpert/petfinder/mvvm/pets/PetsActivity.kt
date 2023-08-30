package com.softxpert.petfinder.mvvm.pets

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.softxpert.petfinder.classes.adapters.PetsAdapter
import com.softxpert.petfinder.classes.adapters.TypesAdapter
import com.softxpert.petfinder.classes.others.CONSTANTS.INTENT.OBJECT
import com.softxpert.petfinder.classes.others.PreferencesHelper
import com.softxpert.petfinder.classes.rest.models.beans.PetBean
import com.softxpert.petfinder.classes.rest.models.responses.PetsResponse
import com.softxpert.petfinder.classes.rest.models.responses.TypesResponse
import com.softxpert.petfinder.classes.ui.EndlessRecyclerViewScrollListener
import com.softxpert.petfinder.databinding.ActivityPetsBinding
import com.softxpert.petfinder.mvvm.pet_detials.PetDetailsActivity
import kotlinx.coroutines.launch

class PetsActivity : AppCompatActivity(), TypesAdapter.Listener, PetsAdapter.Listener,
    AppBarLayout.OnOffsetChangedListener {
    private var selectedType: String = ""
    private val viewModel: PetsViewModel by viewModels()
    private lateinit var binding: ActivityPetsBinding
    private lateinit var typesAdapter: TypesAdapter

    private var currentPage = 0
    private lateinit var petsAdapter: PetsAdapter
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesHelper.init(applicationContext)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserves()
        binding.swipeRefreshLayout.setOnRefreshListener {
            resetState()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        initTypesAdapter()
        initPetsAdapter()
        requestTypes()
    }

    private fun initTypesAdapter() {
        typesAdapter = TypesAdapter(this)
        binding.rvTypes.adapter = typesAdapter
    }

    private fun initPetsAdapter() {
        petsAdapter = PetsAdapter(this)
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener() {
            override fun onLoadMore(page: Int) {
                currentPage = page
                requestPets()
            }
        }
        binding.rvPets.adapter = petsAdapter
        binding.rvPets.addOnScrollListener(endlessRecyclerViewScrollListener)
        currentPage = 1
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

    }

    fun requestTypes() {
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
        petsAdapter.addData(
            response.animals
        )
    }

    override fun onPetClick(pet: PetBean) {
        val intent = Intent(this, PetDetailsActivity::class.java)
        intent.putExtra(OBJECT, pet)
        startActivity(intent)
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
        currentPage = 1
        requestPets()
    }
}