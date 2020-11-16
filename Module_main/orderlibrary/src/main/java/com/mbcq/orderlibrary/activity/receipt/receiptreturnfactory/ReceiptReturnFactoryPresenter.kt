package com.mbcq.orderlibrary.activity.receipt.receiptreturnfactory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-11 09:02:45 回单返厂
 */

class ReceiptReturnFactoryPresenter : BasePresenterImpl<ReceiptReturnFactoryContract.View>(), ReceiptReturnFactoryContract.Presenter {
    /// selType:1代表开单日期，2代表回单寄出，3代表回单返回，4代表回单返厂
    /// HdDirection:1代表所有，2代表发货，3代表到货
    /// HdIsBackState:1代表所有，2代表未签收，3代表已返厂，4代表已返回，5代表已寄出，6代表已签收
    /// GoodsState：1代表所有，2代表已入库，3代表在库，4代表短驳在途，5代表到达，6代表提货签收，7代表派送中，8代表终端外转，9代表送货签收，10代表本地签收，11代表终端签收，12代表异常
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
       // params.put("Page", page)
//        params.put("Limit", 15)
        params.put("SelType", 4)//6按票7按批次

//        params.put("SelWebidCode", selWebidCode)
//        params.put("startDate", startDate)
//        params.put("endDate", endDate)
        get<String>(ApiInterface.RECEIPT_MANAGEMENT_RETURN_FACTORY_LOAD_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val listDta = mutableListOf<ReceiptReturnFactoryBean>()
                    for (index in 0 until it.length()) {
                        val jsonObject: JSONObject = it.getJSONObject(index)
                        val jsonStr = GsonUtils.toPrettyFormat(jsonObject)
                        listDta.add(Gson().fromJson(jsonStr, ReceiptReturnFactoryBean::class.java))
                    }
                    mView?.getPageS(listDta)

                }

            }

        })
    }

    override fun complete(jsonStr: String) {
        post<String>(ApiInterface.RECEIPT_MANAGEMENT_RETURN_FACTORY_OVER_POST, getRequestBody(jsonStr), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeS("")

            }

        })
    }
//
}