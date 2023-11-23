package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.UserBean

data class UserDetailsResponse(var data: UserBean?) :
    BaseResponse()
