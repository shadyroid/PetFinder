package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.UserBean

data class UsersResponse(var data: MutableList<UserBean>?) :
    BaseResponse()
