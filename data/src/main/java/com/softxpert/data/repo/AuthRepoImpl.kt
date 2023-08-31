package com.softxpert.data.repo

import com.softxpert.data.remote.ApiService
import com.softxpert.domain.entity.responses.AuthResponse
import com.softxpert.domain.repo.AuthRepo
import retrofit2.Response


class AuthRepoImpl(private val apiService: ApiService) : AuthRepo {
    override suspend fun requestToken(params: HashMap<String, String>): Response<AuthResponse> =
        apiService.requestToken(params)
}