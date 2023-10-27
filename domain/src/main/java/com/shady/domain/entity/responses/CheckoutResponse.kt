package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.ReceiptBean

data class CheckoutResponse(var data: ReceiptBean?) : BaseResponse()
