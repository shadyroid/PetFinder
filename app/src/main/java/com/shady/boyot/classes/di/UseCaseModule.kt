package com.shady.boyot.classes.di

import com.shady.domain.repo.AuthRepo
import com.shady.domain.repo.InvoicesRepo
import com.shady.domain.repo.UsersRepo
import com.shady.domain.usecase.AuthUseCase
import com.shady.domain.usecase.InvoicesUseCase
import com.shady.domain.usecase.UsersUseCase
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