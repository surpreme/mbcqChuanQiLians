package com.mbcq.orderlibrary.activity.acceptbilling

import com.google.gson.JsonObject
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class AcceptBillingPresenter : BasePresenterImpl<AcceptBillingContract.View>(), AcceptBillingContract.Presenter {
    override fun getWaybillNumber() {
        get<String>(ApiInterface.ACCEPT_BILLING_WAYBILL_NUMBER_GET, null, object : CallBacks {
            override fun onResult(result: String) {
                val mJSONObject = JSONObject(result)
                val mDatas = mJSONObject.optJSONObject("data")
                mDatas?.let {
                    mView?.getWaybillNumberS(it.optString("billno").toString())
                }
            }
        })
    }

    override fun getWebAreaId() {
        try {
            get<String>(ApiInterface.ACCEPT_OUTLET_GET, null, object : CallBacks {
                override fun onResult(result: String) {
                    val mJSONObject = JSONObject(result)
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 1,
    "companyId": 2001,
    "webidCode": 0,
    "webidCodeStr": "",
    "mb": "17530957256",
    "tel": "",
    "shipper": "wxl",
    "companyName": "wxl公司名称测试",
    "idCard": "410482199002265912",
    "address": "wxl发货人地址",
    "vipId": "",
    "vipBankId": "17530957256",
    "isFreeSxf": 0,
    "isFreeSxfStr": "否",
    "opeMan": "汕头",
    "recordDate": "2020-04-09T10:27:33"
    }
    ]}
     */
    override fun getCardNumberCondition(vipBankId: String) {
        val params = HttpParams()
        params.put("vipBankId", vipBankId)
        get<String>(ApiInterface.ACCEPT_SELECT_BANK_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        if (it?.isNull(0)!!) {
                            mView?.getCardNumberConditionNull()
                            return@let
                        }
                        if (it.get(0) != null) {
                            mView?.getCardNumberConditionS(it.get(0).toString())
                        }

                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })

    }

    override fun getBankCardInfo(vipBankId: String) {
        val params = HttpParams()
        params.put("vipBankId", vipBankId)
        get<String>(ApiInterface.ACCEPT_SELECT_BANK_COMPANY_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        val mDatas = it?.getJSONObject(0)
                        val mMemBanDetLst = mDatas?.optJSONArray("MemBanDetLst")
                        mView?.getBankCardInfoS(mMemBanDetLst.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }

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
    override fun getCostInformation(webidCode: String) {
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
                            mView?.getCostInformationS(it.get(0).toString())
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }

    /**
     * post {"ECompanyId":"2001","Destination":"义乌后湖","OrderId":"","WebidCodeStr":"汕头","WebidCode":"1003","Billno":"10030001897","EwebidCodeStr":"义乌后湖","EwebidCode":"1001","OBillno":"","BillDate":"2020-09-09 18:18:59:06","BillState":"","BillTypeStr":"机打","OkProcess":"","OkProcessStr":"客户自提","IsTalkGoods":"0","IsTalkGoodsStr":"否","VipId":"17530957256","ShipperId":"","ShipperMb":"15999999999","ShipperTel":"5555","Shipper":"彭小林","ShipperCid":"","ShipperAddr":"1111","IsUrgent":"0","IsUrgentStr":"否","Transneed":"1","TransneedStr":"零担","ConsigneeMb":"18614079730","ConsigneeTel":"","Consignee":"李紫洋","ConsigneeAddr":"还是","Product":"玻璃1","TotalQty":"","Qty":"1","GoodsNum":"01897-1","Packages":"工工式","Weight":"1","Volumn":"1","AccSum":"200","AccType":"1","AccTypeStr":"现付","BackQty":"签回单","IsWaitNoticeStr":"是","IsWaitNotice":"1","BankCode":"301290000007","BankName":"交通银行","BankMan":"wxl","CreateMan":"lzy","Remark":"12588","FromType":"3","accTrans":"100","accFetch":"1","accPackage":"40","accSend":"1","accGb":"1","accSafe":"1","accRyf":"1","accHuiKou":"2"}
     * success{"code":0,"ljCode":0,"msg":"保存成功!"}
     */
    override fun saveAcceptBilling(job: JSONObject) {
        post<String>(ApiInterface.ACCEPT_SAVE_INFO_POST, getRequestBody(job), object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.saveAcceptBillingS(obj.optString("msg"))
            }

        })
    }


}
