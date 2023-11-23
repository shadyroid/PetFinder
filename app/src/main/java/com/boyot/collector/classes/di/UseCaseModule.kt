package com.boyot.collector.classes.di

import com.boyot.collector.domain.repo.AuthRepo
import com.boyot.collector.domain.repo.InvoicesRepo
import com.boyot.collector.domain.repo.UsersRepo
import com.boyot.collector.domain.usecase.AuthUseCase
import com.boyot.collector.domain.usecase.InvoicesUseCase
import com.boyot.collector.domain.usecase.UsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideInvoicesUseCase(invoicesRepo: InvoicesRepo): InvoicesUseCase {
        return InvoicesUseCase(invoicesRepo)
    }

    @Provides
    fun provideAuthUseCase(authRepo: AuthRepo): AuthUseCase {
        return AuthUseCase(authRepo)
    }

    @Provides
    fun provideUsersUseCase(usersRepo: UsersRepo): UsersUseCase {
        return UsersUseCase(usersRepo)
    }
}