package com.mbcq.accountlibrary.activity.house

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

class HousePresenter : BasePresenterImpl<HouseContract.View>(), HouseContract.Presenter {
    override fun getMenuAuthority() {
        val params = HttpParams()
        params.put("menutype", "app")
        get<String>(ApiInterface.NAVIGATION_ITEM_MENU_AUTHORITY_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getMenuAuthorityS(Gson().fromJson(obj.optString("data"),object:TypeToken<List<AuthorityMenuBean>>(){}.type))
                }
            }

        })
    }

}