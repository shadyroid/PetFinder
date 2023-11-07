package com.shady.domain.repo

import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UnitsResponse
import com.shady.domain.entity.responses.UsersResponse

interface InvoicesRepo {
     suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse
     suspend fun requestReceipts(params: HashMap<String, String>): ReceiptsResponse
     suspend fun requestBuildings(): BuildingsResponse
     suspend fun requestUnits(params: HashMap<String, String>): UnitsResponse
     suspend fun requestCheckout(request: CheckoutRequest): CheckoutResponse
}