package com.mbcq.orderlibrary.activity.stowagealongway

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-21 15:48:12 沿途配载
 */

class StowageAlongWayPresenter : BasePresenterImpl<StowageAlongWayContract.View>(), StowageAlongWayContract.Presenter {
    override fun getPage(page: Int) {
        val params = HttpParams()
        params.put("page", page)
        params.put("limit", 10)
        mView?.getContext()?.let {
            params.put("SelWebidCode", UserInformationUtil.getWebIdCode(it))

        }
        get<String>(ApiInterface.STOWAGE_ALONG_WAY_RECORD_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0))
                        mView?.getPageS(Gson().fromJson(it.optJSONObject(0).optString("data"), object : TypeToken<List<StowageAlongWayBean>>() {}.type))
                }
            }

        })
    }

}