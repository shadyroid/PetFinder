package com.softxpert.domain.entity.responses

import com.softxpert.domain.entity.beans.PetBean

data class PetsResponse (val statusCode: Int,var animals: MutableList<PetBean> )
