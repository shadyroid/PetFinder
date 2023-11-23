package com.boyot.collector.data.repo

import com.boyot.collector.data.remote.ApiService
import com.boyot.collector.domain.entity.beans.BuildingBean
import com.boyot.collector.domain.entity.beans.InvoiceBean
import com.boyot.collector.domain.entity.beans.UserBean
import com.boyot.collector.domain.entity.requests.CheckoutRequest
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.BuildingsResponse
import com.boyot.collector.domain.entity.responses.CheckoutResponse
import com.boyot.collector.domain.entity.responses.GenerateOrderIdResponse
import com.boyot.collector.domain.entity.responses.InvoicesResponse
import com.boyot.collector.domain.entity.responses.LoginResponse
import com.boyot.collector.domain.entity.responses.PaymentNotificationResponse
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import com.boyot.collector.domain.entity.responses.UnitsResponse
import com.boyot.collector.domain.entity.responses.UsersResponse
import com.boyot.collector.domain.repo.InvoicesRepo

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