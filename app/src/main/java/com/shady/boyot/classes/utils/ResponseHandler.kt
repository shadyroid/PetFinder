package com.shady.boyot.classes.utils

import com.shady.domain.entity.responses.BaseResponse


object ResponseHandler {
    fun isSuccess(response: BaseResponse) =
        response.statusCode == 200 || response.statusCode == 201 || response.statusCode == 202

    fun isLogoutCode(response: BaseResponse): Boolean {
        return response.statusCode == 401 || response.statusCode == 410 || response.statusCode == 419 || response.statusCode == 420 || response.statusCode == 426 || response.statusCode == 427 || response.statusCode == 423 || response.statusCode == 421 || response.statusCode == 407
    }

    fun isForceUpdate(response: BaseResponse): Boolean {
        return response.statusCode == 428
    }
}