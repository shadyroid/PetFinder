package com.boyot.collector.domain.repo

import com.boyot.collector.domain.entity.responses.AuthResponse
import com.boyot.collector.domain.entity.responses.LoginResponse
import retrofit2.Response

interface AuthRepo {
     suspend fun requestLogin(body: HashMap<String, String>): LoginResponse
}