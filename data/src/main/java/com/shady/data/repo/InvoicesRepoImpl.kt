package com.shady.data.repo

import com.shady.data.remote.ApiService
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.beans.UserBean
import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.InvoicesRepo

class InvoicesRepoImpl(private val apiService: ApiService) : InvoicesRepo {
    override suspend fun requestInvoices(body: HashMap<String, String>): InvoicesResponse = apiService.requestInvoices(body)
    override suspend fun requestReceipts(body: HashMap<String, String>): ReceiptsResponse = apiService.requestReceipts(body)
    override suspend fun requestBuildings(): BuildingsResponse = apiService.requestBuildings()
    override suspend fun requestCheckout(request: CheckoutRequest): CheckoutResponse = apiService.requestCheckout(request)


}