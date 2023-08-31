package com.softxpert.domain.repo

import com.softxpert.domain.entity.responses.AuthResponse
import retrofit2.Response

interface AuthRepo {
     suspend fun requestToken(params: HashMap<String, String>): Response<AuthResponse>
}