package com.mbcq.orderlibrary.activity.fixedacceptbilling

import com.google.gson.JsonObject
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

class FixedAcceptBillingActivityContract {

    interface View : BaseView {
        fun getTransportModeS(result: String)
        fun getPaymentModeS(result: String)
        fun getWillByInfoS(data: JSONObject)
        fun getWillByInfoNull()
        fun getCostInformationS(result: String,fatherData:JSONObject)
        fun getDestinationS(result: String)
        fun getCargoAppellationS(result: String)
        fun getPackageS(result: String)
        fun getReceiptRequirementS(result: String)
        fun  updateDataS()
        fun getVehicleS(result: String)
        fun getSalesmanS(result: String)
        fun getShipperInfoS(result: String)
        fun getReceiverInfoS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 付款方式
         */
        fun getPaymentMode()
        /**
         * 运输方式
         */
        fun getTransportMode()

        /**
         * 订单的信息
         */
        fun getWillByInfo(billno: String)
        fun getWillByMoreInfo(billno: String)
        /**
         * 获取费用信息 显示的集合
         */
        fun getCostInformation(webidCode: String,fatherData:JSONObject)

        /**
         * 目的地
         */
        fun getDestination(webidCode: String, ewebidCode: String)
        /**
         * 货物 名称
         */
        fun getCargoAppellation()
        /**
         * 包装
         */
        fun getPackage()
        /**
         * 获取回单要求
         */
        fun getReceiptRequirement()
        /**
         * 修改订单
         */
        fun updateData(jsonObject: JSONObject)
        /**
         * 车辆信息
         */
        fun getVehicles()
        /**
         * 业务员
         */

        fun getSalesman()
        /**
         * 发货人
         */
        fun getShipperInfo(params: HttpParams)
        /**
         * 收货人
         */
        fun getReceiverInfo(params: HttpParams)
    }
}
