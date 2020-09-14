package com.mbcq.vehicleslibrary

/**
 * 1短驳
 * 2干线
 */
data class DepartureRecordEvent(
        var type: Int=-1,
        var webCode: String = "",
        var startTime: String = "",
        var endTime: String = ""
)