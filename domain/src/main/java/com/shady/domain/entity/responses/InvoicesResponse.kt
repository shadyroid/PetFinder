package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.InvoiceBean

data class InvoicesResponse(var data: MutableList<InvoiceBean>?) :
    BaseResponse()
