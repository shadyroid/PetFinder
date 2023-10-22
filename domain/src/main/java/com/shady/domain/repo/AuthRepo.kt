package com.shady.domain.repo

import com.shady.domain.entity.responses.AuthResponse
import com.shady.domain.entity.responses.LoginResponse
import retrofit2.Response

interface AuthRepo {
     suspend fun requestLogin(body: HashMap<String, String>): LoginResponse
}