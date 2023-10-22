package com.shady.domain.entity.beans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InvoiceBean(
    val name: String?,
    val id: String?,
    val address: String?,
    val cost: String?,

):Parcelable{
    @Transient
    var isSelected: Boolean = false
}