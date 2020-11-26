package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke

import java.io.Serializable

data class RevokeShortTrunkDepartureScanDataBean(
        var inoneVehicleFlag: String = "",
        var mTotalUnLoadingOrderNum: Int = 0,
        var mTotalLoadingOrderNum: Int = 0
) : Serializable