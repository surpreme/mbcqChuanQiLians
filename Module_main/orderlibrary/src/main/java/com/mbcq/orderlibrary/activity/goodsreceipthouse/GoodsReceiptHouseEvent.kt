package com.mbcq.orderlibrary.activity.goodsreceipthouse

/**
 * 1短驳
 * 2干线
 */
data class GoodsReceiptHouseEvent(
        var type: Int=-1,
        var webCode: String = "",
        var startTime: String = "",
        var endTime: String = ""
)