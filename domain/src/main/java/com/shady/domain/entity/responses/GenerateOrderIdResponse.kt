package com.shady.domain.entity.responses

data class GenerateOrderIdResponse(var data: Data?) : BaseResponse(){
    data class Data(var merchant_reference_id: String?, var amount: String?)
}
