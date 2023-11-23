package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.BuildingBean
import com.boyot.collector.domain.entity.beans.UnitBean

data class UnitsResponse(var data: Data?) : BaseResponse(){
    data class Data(var data: MutableList<UnitBean>?)
}
