package com.shady.domain.entity.responses

import com.shady.domain.entity.beans.ReceiptBean

data class ReceiptsResponse(var data: MutableList<ReceiptBean>?) :
    BaseResponse()
