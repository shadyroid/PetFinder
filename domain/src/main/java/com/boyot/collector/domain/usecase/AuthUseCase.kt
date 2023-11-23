package com.boyot.collector.domain.usecase

import com.boyot.collector.domain.entity.responses.LoginResponse
import com.boyot.collector.domain.repo.AuthRepo

class AuthUseCase(private val authRepo: AuthRepo) {
    suspend fun requestLogin(body: HashMap<String, String>): LoginResponse =
        authRepo.requestLogin(body)
}