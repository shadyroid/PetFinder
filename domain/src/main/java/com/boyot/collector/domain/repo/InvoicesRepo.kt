package com.boyot.collector.domain.repo

import com.boyot.collector.domain.entity.requests.CheckoutRequest
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.BuildingsResponse
import com.boyot.collector.domain.entity.responses.CheckoutResponse
import com.boyot.collector.domain.entity.responses.GenerateOrderIdResponse
import com.boyot.collector.domain.entity.responses.InvoicesResponse
import com.boyot.collector.domain.entity.responses.PaymentNotificationResponse
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import com.boyot.collector.domain.entity.responses.UnitsResponse
import com.boyot.collector.domain.entity.responses.UsersResponse

interface InvoicesRepo {
     suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse
     suspend fun requestReceipts(params: HashMap<String, String>): ReceiptsResponse
     suspend fun requestBuildings(): BuildingsResponse
     suspend fun requestUnits(params: HashMap<String, String>): UnitsResponse
     suspend fun requestCashCheckout(request: CheckoutRequest): CheckoutResponse
     suspend fun requestVisaCheckout(request: CheckoutRequest): CheckoutResponse
     suspend fun requestGenerateOrderId(request: CheckoutRequest): GenerateOrderIdResponse
     suspend fun requestPaymentNotification(params: HashMap<String, String>): PaymentNotificationResponse
}