package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.beans.UnitBean

data class UnitsResponse(var data: Data?) : BaseResponse(){
    data class Data(var data: MutableList<UnitBean>?)
}
