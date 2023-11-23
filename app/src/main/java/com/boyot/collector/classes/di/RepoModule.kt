package com.boyot.collector.classes.di

import com.boyot.collector.data.remote.ApiService
import com.boyot.collector.data.repo.AuthRepoImpl
import com.boyot.collector.data.repo.InvoicesRepoImpl
import com.boyot.collector.data.repo.UsersRepoImpl
import com.boyot.collector.domain.repo.AuthRepo
import com.boyot.collector.domain.repo.InvoicesRepo
import com.boyot.collector.domain.repo.UsersRepo
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

    @Provides
    fun provideUsersRepo(apiService: ApiService): UsersRepo {
        return UsersRepoImpl(apiService)
    }
}