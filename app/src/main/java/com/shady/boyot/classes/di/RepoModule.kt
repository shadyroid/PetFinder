package com.shady.boyot.classes.di

import com.shady.data.remote.ApiService
import com.shady.data.repo.AuthRepoImpl
import com.shady.data.repo.InvoicesRepoImpl
import com.shady.domain.repo.AuthRepo
import com.shady.domain.repo.InvoicesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideInvoicesRepo(apiService: ApiService): InvoicesRepo {
        return InvoicesRepoImpl(apiService)
    }

    @Provides
    fun provideAuthRepo(apiService: ApiService): AuthRepo {
        return AuthRepoImpl(apiService)
    }
}