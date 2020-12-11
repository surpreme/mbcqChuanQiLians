package com.mbcq.orderlibrary.fragment.waybillinformation

data class WaybillInformationTableBean(
        var product: String = "",
        var qty: String = "",
        var packages: String ="",
        var weight: String ="",
        var volumn: String ="",
        var isTitles:Boolean=false)