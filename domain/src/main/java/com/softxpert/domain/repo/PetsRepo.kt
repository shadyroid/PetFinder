package com.softxpert.domain.repo

import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import retrofit2.Response

interface PetsRepo {
     suspend fun requestPets(params: HashMap<String, String>): Response<PetsResponse>
     suspend fun requestTypes(): TypesResponse
}