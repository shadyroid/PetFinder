package com.shady.domain.entity.beans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiptBean(
    val client_name: String?,
    val receipt: String?,
    val invoice_id: String?,
    val total_amount: String?,
    val invoice_number: String?,
    val address: String?,
    val unit_number: String?,
    val region: String?,
    val status: String?,
    val collection_date: String?,
    val service_type: String?,
    val client_phone: String?,
    val payment_method: String?,
    val invoice_date: String?,
    val total_invoice: String?,
    val fees: String?,
    val total_payment: String?,
    ) : Parcelable

//payment
//"data": {
//    "invoice_number": "#2195",
//    "address": "12الجزيرة",
//    "unit_number": "008رقمالوحدة",
//    "region": "الزمالك",
//    "status": 2,
//    "collection_date": "2023فاتورة شهر مايو",
//    "service_type": "إيجارقديم",
//    "client_name": "مجيد محمدبليغ",
//    "client_phone": "101449049",
//    "payment_method": "كاش",
//    "invoice_date": "2023-05-03",
//    "total_invoice": "26.30",
//    "fees": 0,
//    "total_payment": 52.6
//}