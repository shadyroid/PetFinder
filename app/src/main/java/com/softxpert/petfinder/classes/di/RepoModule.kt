package com.softxpert.petfinder.classes.di

import com.softxpert.data.remote.ApiService
import com.softxpert.data.repo.AuthRepoImpl
import com.softxpert.data.repo.PetsRepoImpl
import com.softxpert.domain.repo.AuthRepo
import com.softxpert.domain.repo.PetsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun providePetsRepo(apiService: ApiService): PetsRepo {
        return PetsRepoImpl(apiService)
    }

    @Provides
    fun provideAuthRepo(apiService: ApiService): AuthRepo {
        return AuthRepoImpl(apiService)
    }
}