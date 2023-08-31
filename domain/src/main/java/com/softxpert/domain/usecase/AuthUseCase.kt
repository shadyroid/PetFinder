package com.softxpert.domain.usecase

import com.softxpert.domain.repo.AuthRepo
import com.softxpert.domain.repo.PetsRepo

class AuthUseCase(private val authRepo: AuthRepo) {
    suspend operator fun invoke(params: HashMap<String, String>) = authRepo.requestToken(params)
}