package com.mbcq.orderlibrary.activity.acceptbilling

import com.google.gson.JsonObject
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class AcceptBillingContract {

    interface View : BaseView {
        fun getWaybillNumberS(result: String)
        fun getDestinationS(result: String)
        fun getCargoAppellationS(result: String)
        fun getPackageS(result: String)
        fun getCardNumberConditionS(result: String)
        fun getCardNumberConditionNull()
        fun getBankCardInfoS(result: String)
        fun getReceiptRequirementS(result: String)
        fun getTransportModeS(result: String)
        fun getPaymentModeS(result: String)
        fun getCostInformationS(result: String)
        fun saveAcceptBillingS(result: String, printJson: String, priceJson: String)
        fun getShipperInfoS(result: String)
        fun getReceiverInfoS(result: String)
        fun getVehicleS(result: String)
        fun getSalesmanS(result: String, type: Int)

        /**
         * 通过地址获取经纬度 地理编码
         * 高德 https://developer.amap.com/api/webservice/guide/api/georegeo
         */
        fun getGaoDeAddressLocationS(result: String, latitude: String, longitude: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getWaybillNumber()
        fun getWebAreaId()

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
         * 通过卡号获取银行卡信息
         */
        fun getCardNumberCondition(vipBankId: String)
        fun getBankCardInfo(vipBankId: String)

        /**
         * 获取回单要求
         */
        fun getReceiptRequirement()

        /**
         * 运输方式
         */
        fun getTransportMode()

        /**
         * 付款方式
         */
        fun getPaymentMode()

        /**
         * 获取费用信息 显示的集合
         */
        fun getCostInformation(webidCode: String)

        /**
         * 保存受理开单
         */
        fun saveAcceptBilling(job: JSONObject, printJson: String, priceJson: String)

        /**
         * 发货人
         */
        fun getShipperInfo(params: HttpParams)

        /**
         * 收货人
         */
        fun getReceiverInfo(params: HttpParams)

        /**
         * 车辆信息
         */
        fun getVehicles()

        /**
         * 业务员
         * 1 获取默认值
         * 2 展示
         */

        fun getSalesman(type: Int)

        /**
         * 获取月结客户
         */
        fun getMonthShipperInfo()

        /**
         * 通过地址获取经纬度 地理编码
         * 高德 https://developer.amap.com/api/webservice/guide/api/georegeo
         */
        fun getGaoDeAddressLocation(params: HttpParams, latitude: String, longitude:String)
    }
}
