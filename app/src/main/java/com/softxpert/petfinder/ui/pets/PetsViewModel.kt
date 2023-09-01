package com.softxpert.petfinder.ui.pets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softxpert.domain.entity.responses.AuthResponse
import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import com.softxpert.domain.usecase.AuthUseCase
import com.softxpert.domain.usecase.PetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val petsUseCase: PetsUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val jobs = mutableListOf<Job>()

    private val _typesResponseMutableStateFlow = MutableStateFlow<TypesResponse?>(null)
    private val _petsResponseMutableStateFlow = MutableStateFlow<PetsResponse?>(null)
    private val _authResponseMutableStateFlow = MutableStateFlow<AuthResponse?>(null)

    val typesResponseMutableStateFlow: StateFlow<TypesResponse?> = _typesResponseMutableStateFlow
    val petsResponseMutableStateFlow: StateFlow<PetsResponse?> = _petsResponseMutableStateFlow
    val authResponseMutableStateFlow: StateFlow<AuthResponse?> = _authResponseMutableStateFlow


    fun requestTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = petsUseCase.requestTypes()
                _typesResponseMutableStateFlow.value = response
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun requestPets(body: HashMap<String, String>) {
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = petsUseCase.requestPets(body)
                if (response.code() != 401) {
                    _petsResponseMutableStateFlow.value = response.body()
                } else {
                    requestToken()
                }
            } catch (e: Exception) {
                onError(e)
            }
        })
    }

    private fun requestToken() {
        val body = HashMap<String, String>()
        body["grant_type"] = "client_credentials"
        body["client_id"] = "8PinDztirDQRdfOj1iYpQJwWEcDQyejvqYsBPMKY3ghGcqj4rm"
        body["client_secret"] = "M1WSoKGzpVIrx4cmqphXPxcnBjYO3olCccI1vL1M"
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authUseCase(body)
                _authResponseMutableStateFlow.value = response.body()
            } catch (e: Exception) {
                onError(e)
            }
        })
    }


    private fun onError(e: Throwable) {
        Log.d("error ", "error : $e")
    }

    fun clear() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

}