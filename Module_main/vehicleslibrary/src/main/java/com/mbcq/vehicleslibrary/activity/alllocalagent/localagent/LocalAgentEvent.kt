package com.mbcq.vehicleslibrary.activity.alllocalagent.localagent

/**
 * 1短驳
 * 2干线
 */
data class LocalAgentEvent(
        var type: Int=-1,
        var webCode: String = "",
        var startTime: String = "",
        var endTime: String = ""
)