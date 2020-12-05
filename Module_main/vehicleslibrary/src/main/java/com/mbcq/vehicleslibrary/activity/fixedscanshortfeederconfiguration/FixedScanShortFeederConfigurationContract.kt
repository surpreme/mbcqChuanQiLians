package com.mbcq.vehicleslibrary.activity.fixedscanshortfeederconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-04 10:38:43 修改扫描短驳配置
 */

class FixedScanShortFeederConfigurationContract {

    interface View : BaseView {
        fun saveInfoS(result: String)
        fun getVehicleS(result: String)
        fun geSelectVehicleS(result: String, vehicleNo: String, chaufferMb: String)
        fun getCarInfoS(result: FixedScanShortFeederConfigurationBean)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 车辆信息
         */
        fun getVehicles()
        fun getCarInfo(inoneVehicleFlag: String)

        /**
         * 由于车辆信息只是展示 后台不返回 这里判断车牌号和司机手机号 如果还有雷同找后台
         */
        fun geSelectVehicles(vehicleNo: String, chaufferMb: String)
        fun saveInfo(postJson: String)
    }
}
