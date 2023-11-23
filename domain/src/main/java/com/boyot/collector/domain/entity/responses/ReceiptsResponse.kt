package com.boyot.collector.domain.entity.responses

import com.boyot.collector.domain.entity.beans.ReceiptBean

data class ReceiptsResponse(var data: MutableList<ReceiptBean>?) :
    BaseResponse()
