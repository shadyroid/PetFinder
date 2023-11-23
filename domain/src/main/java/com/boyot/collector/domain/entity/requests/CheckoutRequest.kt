package com.boyot.collector.domain.entity.requests

data class CheckoutRequest(
    var invoice_ids: List<String>?,
    var order_id: String?,
)
