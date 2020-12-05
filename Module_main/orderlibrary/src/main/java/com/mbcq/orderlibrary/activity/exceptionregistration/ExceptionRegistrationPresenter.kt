package com.mbcq.orderlibrary.activity.exceptionregistration

import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-09-28 17:40:00 异常登记
 */

class ExceptionRegistrationPresenter : BasePresenterImpl<ExceptionRegistrationContract.View>(), ExceptionRegistrationContract.Presenter {
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
    override fun getExceptionInfo(billno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getExceptionInfoS(it.getJSONObject(0))

                    } else {
                        mView?.getExceptionInfoNull()
                    }

                }
            }

        })
    }

    override fun postImg(params: HttpParams) {
        post<String>(ApiInterface.POST_PICTURE_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getContext()?.let {
                    val obj = JSONObject(result)

                    mView?.postImgS(obj.optString("data"))

                }

            }

        })
    }

    /**
     * {"code":0,"msg":"","count":7,"data":[
    {
    "id": 1,
    "companyid": 2001,
    "typecode": 1,
    "partypcod": 0,
    "tdescribe": "包装不合格",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 2,
    "companyid": 2001,
    "typecode": 2,
    "partypcod": 0,
    "tdescribe": "开单差错",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 3,
    "companyid": 2001,
    "typecode": 3,
    "partypcod": 0,
    "tdescribe": "涨尺涨寸",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 4,
    "companyid": 2001,
    "typecode": 4,
    "partypcod": 0,
    "tdescribe": "标签差错",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 5,
    "companyid": 2001,
    "typecode": 5,
    "partypcod": 0,
    "tdescribe": "单货不符",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 6,
    "companyid": 2001,
    "typecode": 6,
    "partypcod": 0,
    "tdescribe": "禁运品",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    },
    {
    "id": 7,
    "companyid": 2001,
    "typecode": 7,
    "partypcod": 0,
    "tdescribe": "破损",
    "opeman": "",
    "recorddate": "2018-12-29T13:52:03"
    }
    ]}
     */
    override fun getWrongType() {
        get<String>(ApiInterface.EXCEPTION_RECORD_SELECT_WRONG_TYPE_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getWrongTypeS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }

    override fun updateAllInfo(jsonObject: JSONObject) {
        post<String>(ApiInterface.EXCEPTION_RECORD_ADD_WRONG_POST, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {

            }

        })
    }

    override fun getWrongChildrenType(id: String, companyid: String, typecode: String, partypcod: String, tdescribe: String, opeman: String, recorddate: String) {
        val params = HttpParams()
        params.put("id", id)
        params.put("companyid", companyid)
        params.put("typecode", typecode)
        params.put("partypcod", partypcod)
        params.put("tdescribe", tdescribe)
        params.put("opeman", opeman)
        params.put("recorddate", recorddate)
        get<String>(ApiInterface.EXCEPTION_RECORD_SELECT_WRONG_CHILDREN_TYPE_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getWrongChildrenTypeS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }

    override fun getShortCarNumber(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.WAYBILL_RECORD_SELECT_SHORT_VEHICLES_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getShortCarNumberS(Gson().fromJson(result, ExceptionRegistrationShortCarNumberBean::class.java))
            }

        })
    }

    override fun getDepartureLot(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.WAYBILL_RECORD_SELECT_DEPARTURE_VEHICLES_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getDepartureLotS(Gson().fromJson(result, ExceptionRegistrationDepartureCarNumberBean::class.java))

            }

        })
    }

}