package com.mbcq.orderlibrary.activity.fixedacceptbilling

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
}