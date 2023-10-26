package com.shady.domain.entity.responses

data class LoginResponse(var data: Data?) : BaseResponse(){
    data class Data(var token: String?)

}
