package com.mbcq.orderlibrary.activity.fixedacceptbilling

import com.google.gson.JsonObject
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

class FixedAcceptBillingActivityPresenter : BasePresenterImpl<FixedAcceptBillingActivityContract.View>(), FixedAcceptBillingActivityContract.Presenter {
    /**
     * {"code":0,"msg":"","count":8,"data":[
    {
    "id": 93,
    "type": 14,
    "typestr": "回单要求",
    "companyid": 2001,
    "typecode": 1,
    "tdescribe": "签回单",
    "recorddate": "2018-12-06T17:06:42",
    "typedes": "对运单表(tyd)backqty"
    }
    ]}
    注释 ：：：：这里写死为type=14 获取回单要求
     */
    override fun getReceiptRequirement() {
        val params = HttpParams()
        params.put("type", "14")
        get<String>(ApiInterface.ALLTYPE_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        it?.toString()?.let { it1 ->
                            mView?.getReceiptRequirementS(it1)
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }

    override fun updateData(jsonObject: JsonObject) {
        post<String>(ApiInterface.CHANGE_ORDER_APPLICATION_ADD_POST,getRequestBody(jsonObject),object :CallBacks{
            override fun onResult(result: String) {

            }

        })
    }

    /**
     * {"code":0,"msg":"","count":9,"data":[
    {
    "id": 24,
    "companyId": 2001,
    "webidCode": 1003,
    "webidCodeStr": "",
    "packages": "aaas",
    "opeMan": "张三",
    "recordDate": "2018-12-20T14:14:03"
    }
    ]}
     */
    override fun getPackage() {
        get<String>(ApiInterface.ACCEPT_SELECT_PACKAGE_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getPackageS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }
    /**
     * {"code":0,"msg":"","count":5,"data":[
    {
    "id": 35,
    "companyId": 2001,
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "product": "玻璃",
    "opeMan": "张三",
    "recordDate": "2018-12-21T16:33:39"
    }
    ]}
     */
    override fun getCargoAppellation() {
        get<String>(ApiInterface.ACCEPT_SELECT_CARGO_APPELLATION_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getCargoAppellationS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }
    /**
     * {"code":0,"msg":"","count":4,"data":[
    {
    "id": 57,
    "type": 9,
    "typestr": "货物运输方式",
    "companyid": 2001,
    "typecode": 1,
    "tdescribe": "零担",
    "recorddate": "2018-11-29T09:15:12",
    "typedes": "对应运单表(tyd)transneed"
    }
    ]}
     * 注释 ：：：：这里写死为type=9 获取运输方式
     */
    override fun getTransportMode() {
        val params = HttpParams()
        params.put("type", "9")
        get<String>(ApiInterface.ALLTYPE_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        it?.toString()?.let { it1 ->
                            mView?.getTransportModeS(it1)
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }
    /**
     * {"code":0,"msg":"","count":6,"data":[
    {
    "id": 87,
    "type": 13,
    "typestr": "开单充许付款方式",
    "companyid": 2001,
    "typecode": 1,
    "tdescribe": "现付",
    "recorddate": "2018-11-29T13:40:21",
    "typedes": "对网点配置(webconfig)canacctype"
    }
    ]}     * 注释 ：：：：这里写死为type=13 获取付款方式


     */
    override fun getPaymentMode() {
        val params = HttpParams()
        params.put("type", "13")
        get<String>(ApiInterface.ALLTYPE_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        it?.toString()?.let { it1 ->
                            mView?.getPaymentModeS(it1)
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 168,
    "companyId": 2001,
    "eCompanyId": 2001,
    "orderId": "",
    "billno": "10010000057",
    "oBillno": "",
    "billDate": "2020-10-09T17:09:52",
    "billState": 6,
    "billStateStr": "派送中",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00057-3",
    "okProcess": 2,
    "okProcessStr": "送货上门",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1001,
    "webidCodeStr": "义乌后湖",
    "ewebidCode": 1003,
    "ewebidCodeStr": "汕头",
    "destination": "汕头",
    "transneed": 2,
    "transneedStr": "马帮快线",
    "vipId": "",
    "shipperId": "",
    "shipperMb": "15999999999",
    "shipperTel": "7886",
    "shipper": "彭小林",
    "shipperCid": "",
    "shipperAddr": "1111",
    "consigneeMb": "18614079738",
    "consigneeTel": "552",
    "consignee": "李紫洋",
    "consigneeAddr": "好廉价",
    "product": "玻璃1",
    "totalQty": 3,
    "qty": 3,
    "packages": "工工式",
    "weight": 66.00,
    "volumn": 58.00,
    "weightJs": 0.00,
    "safeMoney": 0.00,
    "accDaiShou": 0.00,
    "accHKChange": 0.00,
    "hkChangeReason": "",
    "sxf": 0.00,
    "wPrice": 0.00,
    "vPrice": 0.00,
    "qtyPrice": 0.00,
    "accNow": 0.00,
    "accArrived": 0.00,
    "accBack": 0.00,
    "accMonth": 0.00,
    "accHuoKuanKou": 0.00,
    "accTrans": 2885.00,
    "accFetch": 55.00,
    "accPackage": 88.00,
    "accSend": 0.00,
    "accGb": 0.00,
    "accSafe": 0.00,
    "accRyf": 0.00,
    "accHuiKou": 0.00,
    "accSms": 0.00,
    "accZz": 0.00,
    "accZx": 0.00,
    "accCb": 0.00,
    "accSl": 0.00,
    "accAz": 0.00,
    "accFj": 0.00,
    "accWz": 0.00,
    "accJc": 0.00,
    "accSum": 3028.00,
    "accType": 2,
    "accTypeStr": "提付",
    "backQty": "签信封",
    "backState": 0,
    "isWaitNotice": 0,
    "isWaitNoticeStr": "否",
    "bankCode": "",
    "bankName": "",
    "bankMan": "",
    "bankNumber": "",
    "createMan": "义乌后湖",
    "salesMan": "",
    "opeMan": "义乌后湖",
    "remark": "",
    "fromType": 3,
    "isTransferCount": 0,
    "preVehicleNo": "",
    "takeWay": "",
    "valueAddedService": "",
    "bilThState": 0,
    "bilThStateStr": ""
    }
    ]}
     */
    override fun getWillByInfo(billno: String) {
        val params= HttpParams()
        params.put("Billno",billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET,params,object:CallBacks{
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)){
                        mView?.getWillByInfoS(it.getJSONObject(0))

                    }else{
                        mView?.getWillByInfoNull()
                    }

                }
            }

        })
    }
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 21,
    "webidCodeStr": "汕头",
    "webidCode": 1003,
    "isCanRecGoo": 1,
    "isCanRecGooStr": null,
    "isUseMonth": 1,
    "isUseMonthStr": null,
    "showCost": "1,2,3,4,5,6,7,8",
    "showCostStr": "基本运费,提货费,打包费,送货费,工本费,保价费,燃油费,返款",
    "showCostFilNam": "accTrans,accFetch,accPackage,accSend,accGb,accSafe,accRyf,accHuiKou",
    "canAccType": "1,2",
    "canAccTypeStr": "现付,提付",
    "accGb": 5.00,
    "limDowAccTrans": 3.00,
    "limDowAccDaiShou": 5.00,
    "canHandBill": 1,
    "canHandBillStr": null,
    "priBillCount": 1,
    "priFetCount": 1,
    "isCanSenDs": 0,
    "isCanSenDsStr": null,
    "isCanRecDs": 0,
    "isCanRecDsStr": null,
    "isMustIdCard": 0,
    "isMustIdCardStr": null,
    "isUseWaiNot": 1,
    "isUseWaiNotStr": null,
    "accNowIsCanHk": 1,
    "accNowIsCanHkStr": null,
    "skipCursor": "",
    "banInfIsWri": 0,
    "banInfIsWriStr": null,
    "arrHowHouCae": 0,
    "accSafe": null,
    "bqWebidCode": 1002,
    "bqWebidCodeStr": "义乌后湖",
    "opeMan": "测试",
    "recordDate": "2019-03-08T15:32:38"
    }
    ]}
     */
    override fun getCostInformation(webidCode: String,fatherData:JSONObject) {
        val params = HttpParams()
        params.put("webidCode", webidCode)
        get<String>(ApiInterface.ACCEPT_SELECT_COST_INFORMATION_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?. getCostInformationS(it.get(0).toString(),fatherData)
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }
    /**
     * {"code":0,"msg":"","count":15,"data":[
    {
    "id": 37,
    "companyId": 2001,
    "belWebCod": 1001,
    "belWebCodStr": "义乌后湖",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "mapDes": "北京1",
    "opeMan": "张三",
    "recordDate": "2019-01-18T17:54:11"
    }
     */
    override fun getDestination(webidCode: String, ewebidCode: String) {
        val params = HttpParams()
        params.put("webidCode", webidCode)
        params.put("ewebidCode", ewebidCode)
        get<String>(ApiInterface.ACCEPT_DESTINATION_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getDestinationS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })

    }
}