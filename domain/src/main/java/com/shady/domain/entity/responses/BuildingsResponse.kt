package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.BuildingBean

data class BuildingsResponse(var data: MutableList<BuildingBean>?) :
    BaseResponse()
