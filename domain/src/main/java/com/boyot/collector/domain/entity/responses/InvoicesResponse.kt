package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.InvoiceBean

data class InvoicesResponse(var data: MutableList<InvoiceBean>?) :
    BaseResponse()
