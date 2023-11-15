package com.shady.domain.entity.beans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InvoiceBean(
    val client_name: String?,
    val invoice_number: String?,
    val invoice_id: String?,
    val address: String?,
    val total_amount: String?,
    val collection_date: String?,

    ):Parcelable{
    @Transient
    var isSelected: Boolean = false
}