package com.mbcq.vehicleslibrary.activity.homedeliveryhouse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 17:59:02
 */

class HomeDeliveryHousePresenter : BasePresenterImpl<HomeDeliveryHouseContract.View>(), HomeDeliveryHouseContract.Presenter {
    override fun getInventory() {
        get<String>(ApiInterface.HOME_DELIVERY_INVENTORY_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInventoryS(Gson().fromJson<List<HomeDeliveryHouseBean>>(obj.optString("data"), object : TypeToken<List<HomeDeliveryHouseBean>>() {}.type))
                }
            }

        })
    }
    override fun saveInfo(ob: JSONObject) {
        post<String>(ApiInterface.COMPELETE_HOME_DELIVERY_HOUSE_POST, getRequestBody(ob), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveInfoS("")

            }

        })
    }
}