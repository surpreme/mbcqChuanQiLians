package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord

/**
 * 1短驳
 * 2干线
 */
data class ArrivalRecordEvent(
        var type: Int=-1,
        var webCode: String = "",
        var startTime: String = "",
        var endTime: String = ""
)