package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.ReceiptBean

data class GenerateOrderIdResponse(var data: Data?) : BaseResponse(){
    data class Data(var order_id: String?,var fees: String?)
}
