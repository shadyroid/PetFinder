package com.softxpert.domain.entity.responses

import com.softxpert.domain.entity.beans.TypeBean

data class TypesResponse (
    var types: MutableList<TypeBean>){
    class builder {
        private var types: MutableList<TypeBean> = mutableListOf()
        fun types(types: MutableList<TypeBean>) = apply { this.types = types }

        fun build() = TypesResponse(types)
    }
}
