package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.BuildingBean

data class BuildingsResponse(var data: Data?) : BaseResponse(){
    data class Data(var data: MutableList<BuildingBean>?)
}
