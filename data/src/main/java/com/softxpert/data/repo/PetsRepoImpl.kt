package com.softxpert.data.repo

import com.softxpert.data.remote.ApiService
import com.softxpert.domain.entity.beans.TypeBean
import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import com.softxpert.domain.repo.PetsRepo
import retrofit2.Response

class PetsRepoImpl(private val apiService: ApiService):PetsRepo {
    override suspend fun requestPets(params: HashMap<String, String>): Response<PetsResponse> = apiService.requestPets(params)
    override suspend fun requestTypes(): TypesResponse = TypesResponse(
        mutableListOf(
            TypeBean("All"),
            TypeBean("Cat"),
            TypeBean("Horse"),
            TypeBean("Bird"),
            TypeBean("Rabbit"),
        )
    )
}