package com.mbcq.orderlibrary.activity.choiceshipper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 11:06:45  选择发货人
 */

class ChoiceShipperPresenter : BasePresenterImpl<ChoiceShipperContract.View>(), ChoiceShipperContract.Presenter {
    override fun getInfo() {
        val params = HttpParams()
        params.put("limit", "9999")
        params.put("page", 1)
        mView?.getContext()?.let {
            params.put("webidCode", UserInformationUtil.getWebIdCode(it))
        }
        get<String>(ApiInterface.SHIPPER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ChoiceShipperBean>>() {}.type))

                }
            }

        })
    }

    override fun deleteShipper(id: String, position: Int) {
        val paramsObj = JSONObject()
        paramsObj.put("id", id)
        post<String>(ApiInterface.SHIPPER_DELETE_INFO_POST, getRequestBody(paramsObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.deleteShipperS(position)
            }

        })

    }

}