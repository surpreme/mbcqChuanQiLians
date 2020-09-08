package com.mbcq.orderlibrary.activity.addreceiver

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

class AddReceiverPresenter : BasePresenterImpl<AddReceiverContract.View>(), AddReceiverContract.Presenter {
    override fun getReceiverInfo(contactMb: String) {
        val params = HttpParams()
        params.put("contactMb", contactMb)
        get<String>(ApiInterface.ACCEPT_SELECT_RECEIVER_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj=  JSONObject(result)
                val datas=obj.opt("data")
                datas?.let {
                    val json = JSONTokener(it.toString()).nextValue()
                    if (json is JSONArray){
                        mView?.getReceiverInfoS(it.toString())

                    }

                }
            }

        })
    }

}