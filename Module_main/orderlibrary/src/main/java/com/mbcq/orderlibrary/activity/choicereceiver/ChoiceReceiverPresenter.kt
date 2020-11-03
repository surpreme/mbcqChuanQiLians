package com.mbcq.orderlibrary.activity.choicereceiver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.choiceshipper.ChoiceShipperBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 15:08:32 选择收货人
 */

class ChoiceReceiverPresenter : BasePresenterImpl<ChoiceReceiverContract.View>(), ChoiceReceiverContract.Presenter {
    override fun getInfo() {
        get<String>(ApiInterface.RECEIVER_SELECT_INFO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ChoiceReceiverBean>>() {}.type))

                }
            }

        })
    }
}