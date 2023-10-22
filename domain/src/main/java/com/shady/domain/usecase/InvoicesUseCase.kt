package com.shady.domain.usecase

import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.InvoicesRepo

class InvoicesUseCase(private val invoicesRepo: InvoicesRepo) {
    suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse =
        invoicesRepo.requestInvoices(params)
    suspend fun requestUsers(params: HashMap<String, String>): UsersResponse =
        invoicesRepo.requestUsers(params)

    suspend fun requestBuildings(): BuildingsResponse =
        invoicesRepo.requestBuildings()


}