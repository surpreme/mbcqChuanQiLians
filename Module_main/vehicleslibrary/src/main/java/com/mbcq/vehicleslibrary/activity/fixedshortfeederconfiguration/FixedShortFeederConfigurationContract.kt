package com.mbcq.vehicleslibrary.activity.fixedshortfeederconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.fixedscanshortfeederconfiguration.FixedScanShortFeederConfigurationBean

/**
 * @author: lzy
 * @time: 2020-12-21 14:42:34 修改短驳配置信息
 */

class FixedShortFeederConfigurationContract {

    interface View : BaseView {
        fun getVehicleS(result: String)
        fun getCarInfoS(result: FixedScanShortFeederConfigurationBean)
        fun geSelectVehicleS(result: String, vehicleNo: String, chaufferMb: String)
        fun saveInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 车辆信息
         */
        fun getVehicles()

        /**
         * 到货网点
         */
        fun geDeliveryPoint(type:Int)
        fun getCarInfo(inoneVehicleFlag: String)
        /**
         * 由于车辆信息只是展示 后台不返回 这里判断车牌号和司机手机号 如果还有雷同找后台
         */
        fun geSelectVehicles(vehicleNo: String, chaufferMb: String)
        fun saveInfo(postJson: String)

    }
}
