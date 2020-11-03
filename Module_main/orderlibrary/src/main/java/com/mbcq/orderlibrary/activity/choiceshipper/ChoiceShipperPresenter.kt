package com.mbcq.orderlibrary.activity.choiceshipper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 11:06:45  选择发货人
 */

class ChoiceShipperPresenter : BasePresenterImpl<ChoiceShipperContract.View>(), ChoiceShipperContract.Presenter {
    override fun getInfo() {
        get<String>(ApiInterface.SHIPPER_SELECT_INFO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ChoiceShipperBean>>() {}.type))

                }
            }

        })
    }

}