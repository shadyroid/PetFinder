package com.shady.domain.entity.responses

open class BaseResponse (
    var message: String? = "",
    var status_code: Int = 200
)