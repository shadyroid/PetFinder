package com.softxpert.petfinder.classes.rest.models.beans

data class TypeBean(
    var name: String,
) {
    class builder {
        private var name: String = ""
        fun name(name: String) = apply { this.name = name }

        fun build() = TypeBean(name)
    }
} 