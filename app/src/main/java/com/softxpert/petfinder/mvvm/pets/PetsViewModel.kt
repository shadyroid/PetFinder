package com.softxpert.petfinder.mvvm.pets

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.softxpert.petfinder.classes.others.PreferencesHelper
import com.softxpert.petfinder.classes.rest.models.responses.PetsResponse
import com.softxpert.petfinder.classes.rest.models.responses.TypesResponse
import com.softxpert.petfinder.mvvm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PetsViewModel : BaseViewModel() {
    val typesResponseMutableStateFlow = MutableStateFlow<TypesResponse?>(null)
    val petsResponseMutableStateFlow = MutableStateFlow<PetsResponse?>(null)

    fun requestTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.requestTypes()
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
                val response = repository.requestPets(body)
                if (isSuccess(response)) {
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
                val response = repository.requestToken(tokenBody)
                withContext(Dispatchers.Main) {
                    PreferencesHelper.putAuthToken(response.body()?.access_token)
                }
                requestPets(body);
            } catch (e: Exception) {
                onError(e)
            }
        })
    }

    private fun isSuccess(response: Response<PetsResponse>): Boolean {
        return response.code() != 401;
    }



}