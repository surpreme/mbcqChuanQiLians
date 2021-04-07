package com.mbcq.orderlibrary.activity.choicereceiver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 15:08:32 选择收货人
 */

class ChoiceReceiverPresenter : BasePresenterImpl<ChoiceReceiverContract.View>(), ChoiceReceiverContract.Presenter {
    override fun getInfo() {
        val params = HttpParams()
        params.put("limit", "9999")
        params.put("page", 1)
        mView?.getContext()?.let {
            params.put("webidCode", UserInformationUtil.getWebIdCode(it))
        }
        get<String>(ApiInterface.RECEIVER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ChoiceReceiverBean>>() {}.type))

                }
            }

        })
    }

    override fun deleteReceiver(id: String, position: Int) {
        val paramsObj = JSONObject()
        paramsObj.put("id", id)
        post<String>(ApiInterface.RECEIVER_DELETE_INFO_POST, getRequestBody(paramsObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.deleteReceiverS(position)
            }

        })
    }
}