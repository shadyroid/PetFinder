package com.softxpert.petfinder.classes.di

import com.softxpert.domain.repo.AuthRepo
import com.softxpert.domain.repo.PetsRepo
import com.softxpert.domain.usecase.AuthUseCase
import com.softxpert.domain.usecase.PetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providePetsUseCase(petsRepo: PetsRepo): PetsUseCase {
        return PetsUseCase(petsRepo)
    }
    @Provides
    fun provideAuthUseCase(authRepo: AuthRepo): AuthUseCase {
        return AuthUseCase(authRepo)
    }
}