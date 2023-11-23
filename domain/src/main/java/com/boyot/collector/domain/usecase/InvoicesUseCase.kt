package com.boyot.collector.domain.usecase

import com.boyot.collector.domain.entity.requests.CheckoutRequest
import com.boyot.collector.domain.entity.responses.BuildingsResponse
import com.boyot.collector.domain.entity.responses.CheckoutResponse
import com.boyot.collector.domain.entity.responses.GenerateOrderIdResponse
import com.boyot.collector.domain.entity.responses.InvoicesResponse
import com.boyot.collector.domain.entity.responses.PaymentNotificationResponse
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import com.boyot.collector.domain.entity.responses.UnitsResponse
import com.boyot.collector.domain.repo.InvoicesRepo

class InvoicesUseCase(private val invoicesRepo: InvoicesRepo) {
    suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse =
        invoicesRepo.requestInvoices(params)

    suspend fun requestReceipts(params: HashMap<String, String>): ReceiptsResponse =
        invoicesRepo.requestReceipts(params)

    suspend fun requestBuildings(): BuildingsResponse =
        invoicesRepo.requestBuildings()

    suspend fun requestCashCheckout(request: CheckoutRequest): CheckoutResponse =
        invoicesRepo.requestCashCheckout(request)
  suspend fun requestVisaCheckout(request: CheckoutRequest): CheckoutResponse =
        invoicesRepo.requestVisaCheckout(request)

suspend fun requestGenerateOrderId(request: CheckoutRequest): GenerateOrderIdResponse =
        invoicesRepo.requestGenerateOrderId(request)

    suspend fun requestUnits(params: HashMap<String, String>): UnitsResponse =
        invoicesRepo.requestUnits(params)

    suspend fun requestPaymentNotification(params: HashMap<String, String>): PaymentNotificationResponse =
        invoicesRepo.requestPaymentNotification(params)


}