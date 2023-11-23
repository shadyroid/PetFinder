package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.BuildingBean

data class BuildingsResponse(var data: Data?) : BaseResponse(){
    data class Data(var data: MutableList<BuildingBean>?)
}
