package com.shady.domain.repo

import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.UsersResponse

interface InvoicesRepo {
     suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse
     suspend fun requestUsers(params: HashMap<String, String>): UsersResponse
     suspend fun requestBuildings(): BuildingsResponse
}