package com.softxpert.domain.usecase

import com.softxpert.domain.entity.beans.TypeBean
import com.softxpert.domain.entity.responses.PetsResponse
import com.softxpert.domain.entity.responses.TypesResponse
import com.softxpert.domain.repo.PetsRepo
import retrofit2.Response

class PetsUseCase(private val petsRepo: PetsRepo) {
    suspend fun requestPets(params: HashMap<String, String>): Response<PetsResponse> = petsRepo.requestPets(params)

    suspend fun requestTypes(): TypesResponse = TypesResponse.builder()
        .types(
            mutableListOf(
                TypeBean.builder()
                    .name("All")
                    .build(),
                TypeBean.builder()
                    .name("Cat")
                    .build(),
                TypeBean.builder()
                    .name("Horse")
                    .build(),
                TypeBean.builder()
                    .name("Bird")
                    .build(),
                TypeBean.builder()
                    .name("Rabbit")
                    .build(),

                )
        )
        .build()
}