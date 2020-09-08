package com.mbcq.orderlibrary.activity.addshipper

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.ResultDataCallBack
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020.09.24
 */

class AddShipperPresenter : BasePresenterImpl<AddShipperContract.View>(), AddShipperContract.Presenter {
    /**
     *
    {
    "id": 95,
    "companyId": 2001,
    "vipId": "",
    "companyName": "",
    "cType": 0,
    "cTypeStr": "收录",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "contactMan": "11",
    "contactTel": "11111",
    "contactMb": "15999999999",
    "address": "1111111111",
    "isMonth": 0,
    "isMonthStr": "否",
    "taxPoint": 0.00,
    "accTimeLimit": 0,
    "accCredit": 0.00,
    "expireDate": "1900-01-01T00:00:00",
    "idCard": "",
    "shaWebCod": "",
    "shaWebCodStr": "",
    "canAccTyp": 0,
    "canAccTypStr": "",
    "qtyPrice": 0.00,
    "wPrice": 0.00,
    "vPrice": 0.00,
    "salesMan": "",
    "product": "1",
    "package": "11",
    "accType": 2,
    "accTypStr": "提付",
    "opeMan": "彭小林",
    "recordDate": "2020-08-25T18:43:28"
    }
     */
    override fun getShipperInfo(contactMb: String) {
        val params = HttpParams()
        params.put("contactMb", contactMb)
        get<String>(ApiInterface.ACCEPT_SELECT_SHIPPER_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj=  JSONObject(result)
                val datas=obj.opt("data")
                datas?.let {
                    val json = JSONTokener(it.toString()).nextValue()
                    if (json is JSONArray){
                        mView?.getShipperInfoS(it.toString())

                    }

                }
            }

        })
    }

}