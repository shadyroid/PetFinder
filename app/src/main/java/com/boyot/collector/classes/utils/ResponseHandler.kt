package com.boyot.collector.classes.utils

import com.boyot.collector.domain.entity.responses.BaseResponse


object ResponseHandler {
    fun isSuccess(response: BaseResponse) =
        response.status_code == 200 || response.status_code == 201 || response.status_code == 202

    fun isLogoutCode(response: BaseResponse): Boolean {
        return response.status_code == 401
    }

    fun isForceUpdate(response: BaseResponse): Boolean {
        return response.status_code == 428
    }
}