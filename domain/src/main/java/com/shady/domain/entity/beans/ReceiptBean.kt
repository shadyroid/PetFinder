package com.shady.domain.entity.beans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiptBean(
    val client_name: String?,
    val receipt: String?,
    val invoice_id: String?,
    val address: String?,
    val total_amount: String?,

    ):Parcelable{
}