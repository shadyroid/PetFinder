package com.boyot.collector.data.repo

import com.boyot.collector.data.remote.ApiService
import com.boyot.collector.domain.entity.responses.AuthResponse
import com.boyot.collector.domain.entity.responses.LoginResponse
import com.boyot.collector.domain.repo.AuthRepo
import retrofit2.Response


class AuthRepoImpl(private val apiService: ApiService) : AuthRepo {
    override suspend fun requestLogin(body: HashMap<String, String>): LoginResponse = apiService.requestLogin(body)
}