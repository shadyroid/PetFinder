package com.softxpert.domain.entity.beans


data class TypeBean(
    var name: String,
) {
    class builder {
        private var name: String = ""
        fun name(name: String) = apply { this.name = name }

        fun build() = TypeBean(name)
    }
} 