package com.softxpert.petfinder.ui.pets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softxpert.data.preferenceses.PreferencesHelper
import com.softxpert.domain.entity.responses.AuthResponse
import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import com.softxpert.domain.usecase.AuthUseCase
import com.softxpert.domain.usecase.PetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(private val petsUseCase: PetsUseCase,private val authUseCase: AuthUseCase) : ViewModel() {
    val typesResponseMutableStateFlow = MutableStateFlow<TypesResponse?>(null)
    val petsResponseMutableStateFlow = MutableStateFlow<PetsResponse?>(null)
    val authResponseMutableStateFlow = MutableStateFlow<AuthResponse?>(null)
    private val jobs = mutableListOf<Job>()


    fun onError(e: Throwable) {
        Log.d("crash_tryObserver", "error : $e")
    }

    fun requestTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = petsUseCase.requestTypes()
                withContext(Dispatchers.Main) {
                    typesResponseMutableStateFlow.value = response
                }
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
                    petsResponseMutableStateFlow.value = response.body()
                } else {
                    requestToken(body);
                }
            } catch (e: Exception) {
                onError(e)
            }
        })
    }

    private fun requestToken(body: HashMap<String, String>) {
        val tokenBody = HashMap<String, String>()
        tokenBody["grant_type"] = "client_credentials"
        tokenBody["client_id"] = "8PinDztirDQRdfOj1iYpQJwWEcDQyejvqYsBPMKY3ghGcqj4rm"
        tokenBody["client_secret"] = "M1WSoKGzpVIrx4cmqphXPxcnBjYO3olCccI1vL1M"
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authUseCase(tokenBody)
                authResponseMutableStateFlow.value = response.body()
            } catch (e: Exception) {
                onError(e)
            }
        })
    }

    fun clear() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

}