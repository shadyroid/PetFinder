package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.ReceiptBean

data class CheckoutResponse(var data: ReceiptBean?) : BaseResponse()
