package com.shady.data.repo

import com.shady.data.remote.ApiService
import com.shady.domain.entity.responses.AuthResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.repo.AuthRepo
import retrofit2.Response


class AuthRepoImpl(private val apiService: ApiService) : AuthRepo {
    override suspend fun requestLogin(body: HashMap<String, String>): LoginResponse = LoginResponse("sdsadsadsadsadsadsad")
}