package com.shady.data.repo

import com.shady.data.remote.ApiService
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.beans.UserBean
import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.entity.responses.GenerateOrderIdResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.entity.responses.PaymentNotificationResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UnitsResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.InvoicesRepo

class InvoicesRepoImpl(private val apiService: ApiService) : InvoicesRepo {
    override suspend fun requestInvoices(body: HashMap<String, String>): InvoicesResponse = apiService.requestInvoices(body)
    override suspend fun requestReceipts(body: HashMap<String, String>): ReceiptsResponse = apiService.requestReceipts(body)
    override suspend fun requestBuildings(): BuildingsResponse = apiService.requestBuildings()
    override suspend fun requestUnits(body: HashMap<String, String>): UnitsResponse = apiService.requestUnits(body)
    override suspend fun requestCashCheckout(request: CheckoutRequest): CheckoutResponse = apiService.requestCashCheckout(request)
    override suspend fun requestVisaCheckout(request: CheckoutRequest): CheckoutResponse = apiService.requestVisaCheckout(request)
    override suspend fun requestGenerateOrderId(request: CheckoutRequest): GenerateOrderIdResponse = apiService.requestGenerateOrderId(request)
    override suspend fun requestPaymentNotification(body: HashMap<String, String>): PaymentNotificationResponse = apiService.requestPaymentNotification(body)


}