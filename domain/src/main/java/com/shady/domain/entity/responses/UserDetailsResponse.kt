package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.UserBean

data class UserDetailsResponse(var data: UserBean?) :
    BaseResponse()
