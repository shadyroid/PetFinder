package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.UnitBean

data class PaymentNotificationResponse(var data: Data?) : BaseResponse(){
    data class Data(var total_invoices_not_paid: String?, var amount_of_invoices: String?, var client_name: String?, var client_phone: String?, var unit: List<UnitBean>?)
}
