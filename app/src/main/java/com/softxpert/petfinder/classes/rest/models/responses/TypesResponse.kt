package com.softxpert.petfinder.classes.rest.models.responses

import com.softxpert.petfinder.classes.rest.models.beans.TypeBean

data class TypesResponse (
    var types: MutableList<TypeBean>){
    class builder {
        private var types: MutableList<TypeBean> = mutableListOf()
        fun types(types: MutableList<TypeBean>) = apply { this.types = types }

        fun build() = TypesResponse(types)
    }
}
