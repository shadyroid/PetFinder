package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.UserBean

data class UsersResponse(var data: MutableList<UserBean>?) :
    BaseResponse()
