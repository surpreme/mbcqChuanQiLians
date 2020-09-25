package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagent

/**
 * 1按车
 * 2按票
 */
data class TerminalAgentEvent(
        var type: Int=-1,
        var webCode: String = "",
        var startTime: String = "",
        var endTime: String = ""
)