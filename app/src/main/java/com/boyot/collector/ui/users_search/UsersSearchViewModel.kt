package com.boyot.collector.ui.users_search

import androidx.lifecycle.viewModelScope
import com.boyot.collector.base.BaseViewModel
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.domain.entity.responses.BuildingsResponse
import com.boyot.collector.domain.entity.responses.UnitsResponse
import com.boyot.collector.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersSearchViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _buildingsResponseMutableStateFlow = MutableStateFlow<BuildingsResponse?>(null)

    val buildingsResponseMutableStateFlow: StateFlow<BuildingsResponse?> = _buildingsResponseMutableStateFlow


    private val _unitsResponseMutableStateFlow = MutableStateFlow<UnitsResponse?>(null)
    val unitsResponseMutableStateFlow: StateFlow<UnitsResponse?> = _unitsResponseMutableStateFlow



    fun requestBuildings() {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestBuildings()
                if (ResponseHandler.isSuccess(response)) {
                    _buildingsResponseMutableStateFlow.value = response
                } else {
                    apiErrorMutableStateFlow.value = response
                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                loadingMutableStateFlow.value = false
            }
        })

    }
    fun requestUnits(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestUnits(body)
                if (ResponseHandler.isSuccess(response)) {
                    _unitsResponseMutableStateFlow.value = response
                } else {
                    apiErrorMutableStateFlow.value = response
                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                loadingMutableStateFlow.value = false
            }
        })

    }
}