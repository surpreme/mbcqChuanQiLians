package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke

import java.io.Serializable

data class RevokeDepartureTrunkDepartureScanDataBean(
        var inoneVehicleFlag: String= "",
        var mTotalUnLoadingOrderNum: Int = 0,
        var mTotalLoadingOrderNum: Int = 0
) : Serializable
