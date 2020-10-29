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

    override fun updateData(jsonObject: JSONObject) {
        post<String>(ApiInterface.CHANGE_ORDER_APPLICATION_ADD_POST, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                mView?.updateDataS()
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
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWillByInfoS(it.getJSONObject(0))

                    } else {
                        mView?.getWillByInfoNull()
                    }

                }
            }

        })
    }

    /**
     * {
    "id": 186,
    "eCompanyId": 2001,
    "orderId": "",
    "billno": "10030002172",
    "oBillno": "",
    "billDate": "2020-10-26T15:20:25",
    "billState": 4,
    "billStateStr": "到达",
    "billType": 0,
    "bilThState": 0,
    "bilThStateStr": "",
    "billTypeStr": "机打",
    "goodsNum": "02172-3",
    "okProcess": 2,
    "okProcessStr": "送货上门",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 1,
    "isTalkGoodsStr": "是",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "destination": "义乌后湖",
    "transneed": 3,
    "transneedStr": "整车",
    "vipId": "",
    "shipperId": "",
    "shipperMb": "18614079732",
    "shipperTel": "00688",
    "shipper": "李紫洋",
    "shipperCid": "",
    "shipperAddr": "南极洲",
    "consigneeMb": "18614079738",
    "consigneeTel": "",
    "consignee": "王可可",
    "consigneeAddr": "澳大利亚",
    "product": "北极熊",
    "totalQty": 3,
    "qty": 3,
    "packages": "冰块",
    "weight": 6000,
    "volumn": 4000,
    "weightJs": 0,
    "safeMoney": 0,
    "accDaiShou": 0,
    "accHKChange": 0,
    "hkChangeReason": "",
    "sxf": 0,
    "wPrice": 0,
    "vPrice": 0,
    "qtyPrice": 0,
    "accNow": 0,
    "accArrived": 0,
    "accBack": 0,
    "accMonth": 0,
    "accHuoKuanKou": 0,
    "accTrans": 12000,
    "accFetch": 0,
    "accPackage": 3450,
    "accSend": 0,
    "accGb": 0,
    "accSafe": 0,
    "accRyf": 0,
    "accHuiKou": 0,
    "accSms": 0,
    "accZz": 0,
    "accZx": 0,
    "accCb": 0,
    "accSl": 0,
    "accAz": 0,
    "accFj": 0,
    "accWz": 0,
    "accJc": 0,
    "accSum": 15450,
    "accType": 3,
    "accTypeStr": "回单付",
    "backQty": "打收条",
    "backState": 0,
    "isWaitNotice": 0,
    "isWaitNoticeStr": "否",
    "bankCode": "",
    "bankName": "",
    "bankMan": "",
    "bankNumber": "",
    "createMan": "lzy",
    "salesMan": "",
    "remark": "",
    "fromType": 3,
    "isTransferCount": 0,
    "preVehicleNo": "",
    "valueAddedService": "",
    "billnos": null,
    "fromTypeStr": "",
    "proCode": 0,
    "Id": 186,
    "ECompanyId": 2001,
    "OrderId": "",
    "Billno": "10030002172",
    "OBillno": "",
    "BillDate": "2020-10-26T15:20:25",
    "BillState": 4,
    "BilThState": 0,
    "BilThStateStr": "",
    "BillStateStr": "到达",
    "BillType": 0,
    "BillTypeStr": "机打",
    "GoodsNum": "02172-3",
    "OkProcess": 2,
    "OkProcessStr": "送货上门",
    "IsUrgent": 0,
    "IsUrgentStr": "否",
    "IsTalkGoods": 1,
    "IsTalkGoodsStr": "是",
    "WebidCode": 1003,
    "WebidCodeStr": "汕头",
    "EwebidCode": 1001,
    "EwebidCodeStr": "义乌后湖",
    "Destination": "义乌后湖",
    "Transneed": 3,
    "TransneedStr": "整车",
    "VipId": "",
    "ShipperId": "",
    "ShipperMb": "18614079732",
    "ShipperTel": "00688",
    "Shipper": "李紫洋",
    "ShipperCid": "",
    "ShipperAddr": "南极洲",
    "ConsigneeMb": "18614079738",
    "ConsigneeTel": "",
    "Consignee": "王可可",
    "ConsigneeAddr": "澳大利亚",
    "Product": "北极熊",
    "TotalQty": 3,
    "Qty": 3,
    "Packages": "冰块",
    "Weight": 6000,
    "Volumn": 4000,
    "WeightJs": 0,
    "SafeMoney": 0,
    "AccDaiShou": 0,
    "AccHKChange": 0,
    "HkChangeReason": "",
    "Sxf": 0,
    "WPrice": 0,
    "VPrice": 0,
    "QtyPrice": 0,
    "AccNow": 0,
    "AccArrived": 0,
    "AccBack": 0,
    "AccMonth": 0,
    "AccHuoKuanKou": 0,
    "AccTrans": 12000,
    "AccFetch": 0,
    "AccPackage": 3450,
    "AccSend": 0,
    "AccGb": 0,
    "AccSafe": 0,
    "AccRyf": 0,
    "AccHuiKou": 0,
    "AccSms": 0,
    "AccZz": 0,
    "AccZx": 0,
    "AccCb": 0,
    "AccSl": 0,
    "AccAz": 0,
    "AccFj": 0,
    "AccWz": 0,
    "AccJc": 0,
    "AccSum": 15450,
    "AccType": 3,
    "AccTypeStr": "回单付",
    "BackQty": "打收条",
    "BackState": 0,
    "IsWaitNotice": 0,
    "IsWaitNoticeStr": "否",
    "BankCode": "",
    "BankName": "",
    "BankMan": "",
    "BankNumber": "",
    "CreateMan": "lzy",
    "SalesMan": "",
    "Remark": "",
    "FromType": 3,
    "IsTransferCount": 0,
    "PreVehicleNo": "",
    "ValueAddedService": "",
    "WayGoosLst": [
    {
    "billno": "10030002172",
    "product": "北极熊",
    "qty": 3,
    "packages": "冰块",
    "weight": 6000,
    "volumn": 4000,
    "weightJs": 0,
    "fromType": 0,
    "fromTypeStr": "",
    "proCode": 0,
    "Billno": "10030002172",
    "Product": "北极熊",
    "Qty": 3,
    "Packages": "冰块",
    "Weight": 6000,
    "Volumn": 4000,
    "WeightJs": 0,
    "CompanyId": 0,
    "OpeMan": null,
    "OpeManNum": 0,
    "SelWebidCode": null,
    "SelEwebidCode": null,
    "LoginWebidCode": 0,
    "LoginWebidCodeStr": null,
    "Page": 1,
    "Limit": 10,
    "SelType": 0,
    "SelLoc": 0,
    "UpdType": 0,
    "DelType": 0,
    "StartDate": "0001-01-01T00:00:00",
    "EndDate": "0001-01-01T00:00:00",
    "LDataTable": null,
    "LMySqlTransaction": null,
    "SqlOrPro": "",
    "LCommandType": 1,
    "LMySqlCommand": null,
    "CommonStr": null,
    "FromType": 0,
    "FromTypeStr": "",
    "ProCode": 0
    }
    ],
    "WaybillState": {
    "id": 0,
    "billno": "",
    "reportState": 0,
    "reportStateStr": "",
    "reportDate": "1900-01-01T00:00:00",
    "reportMan": "",
    "conRepSta": 0,
    "conRepStaStr": "",
    "conRepDat": "1900-01-01T00:00:00",
    "conRepMan": "",
    "hkIsBack": 0,
    "hkIsOut": 0,
    "accArrivedState": "1900-01-01T00:00:00",
    "hkIsArrived": 0,
    "hkArrivedDate": "1900-01-01T00:00:00",
    "hkArrivedMan": "",
    "isReturn": 0,
    "isReturnStr": "",
    "planReturnDt": "1900-01-01T00:00:00",
    "planReTurnMan": "",
    "confirmReturnDt": "1900-01-01T00:00:00",
    "confirmReturnMan": "",
    "accTransState": 0,
    "accTransStateStr": "",
    "fromType": 0,
    "fromTypeStr": "",
    "proCode": 0,
    "AccTransState": 0,
    "AccTransStateStr": "",
    "Id": 0,
    "Billno": "",
    "ReportState": 0,
    "ReportStateStr": "",
    "ReportDate": "1900-01-01T00:00:00",
    "ReportMan": "",
    "ConRepSta": 0,
    "ConRepStaStr": "",
    "ConRepDat": "1900-01-01T00:00:00",
    "ConRepMan": "",
    "HkIsBack": 0,
    "HkIsOut": 0,
    "AccArrivedState": "1900-01-01T00:00:00",
    "HkIsArrived": 0,
    "HkArrivedDate": "1900-01-01T00:00:00",
    "HkArrivedMan": "",
    "IsReturn": 0,
    "IsReturnStr": "",
    "PlanReturnDt": "1900-01-01T00:00:00",
    "PlanReTurnMan": "",
    "ConfirmReturnDt": "1900-01-01T00:00:00",
    "ConfirmReturnMan": "",
    "CompanyId": 0,
    "OpeMan": null,
    "OpeManNum": 0,
    "SelWebidCode": null,
    "SelEwebidCode": null,
    "LoginWebidCode": 0,
    "LoginWebidCodeStr": null,
    "Page": 1,
    "Limit": 10,
    "SelType": 0,
    "SelLoc": 0,
    "UpdType": 0,
    "DelType": 0,
    "StartDate": "0001-01-01T00:00:00",
    "EndDate": "0001-01-01T00:00:00",
    "LDataTable": null,
    "LMySqlTransaction": null,
    "SqlOrPro": "",
    "LCommandType": 1,
    "LMySqlCommand": null,
    "CommonStr": null,
    "FromType": 0,
    "FromTypeStr": "",
    "ProCode": 0
    },
    "Billnos": null,
    "CompanyId": 2001,
    "OpeMan": "lzy",
    "OpeManNum": 0,
    "SelWebidCode": null,
    "SelEwebidCode": null,
    "LoginWebidCode": 0,
    "LoginWebidCodeStr": null,
    "Page": 1,
    "Limit": 10,
    "SelType": 0,
    "SelLoc": 0,
    "UpdType": 0,
    "DelType": 0,
    "StartDate": "0001-01-01T00:00:00",
    "EndDate": "0001-01-01T00:00:00",
    "LDataTable": null,
    "LMySqlTransaction": null,
    "SqlOrPro": "",
    "LCommandType": 1,
    "LMySqlCommand": null,
    "CommonStr": null,
    "FromTypeStr": "",
    "ProCode": 0
    }
     */
    override fun getWillByMoreInfo(billno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWillByInfoS(it.getJSONObject(0))

                    } else {
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
    override fun getCostInformation(webidCode: String, fatherData: JSONObject) {
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
                            mView?.getCostInformationS(it.get(0).toString(), fatherData)
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