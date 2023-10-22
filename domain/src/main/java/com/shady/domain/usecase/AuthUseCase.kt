package com.shady.domain.usecase

import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.repo.AuthRepo

class AuthUseCase(private val authRepo: AuthRepo) {
    suspend fun requestLogin(body: HashMap<String, String>): LoginResponse =
        authRepo.requestLogin(body)
}