package com.mbcq.vehicleslibrary.activity.fixhomedeliveryconfiguration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.homedelivery.HomeDeliveryBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseBean
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2021-03-27 17:53:06 修改上门提货配置
 */

class FixHomeDeliveryConfigurationPresenter : BasePresenterImpl<FixHomeDeliveryConfigurationContract.View>(), FixHomeDeliveryConfigurationContract.Presenter {


    override fun getVehicles() {
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getVehicleS(obj.optString("data"))
                        }
                    }

                }


            }

        })
    }

    override fun fixConfiguration(dataStr: String) {
        val params = HttpParams()
        params.put("id", JSONObject(dataStr).optString("id"))
        get<String>(ApiInterface.HOME_DELIVERY_LOADING_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val resultList = Gson().fromJson<List<HomeDeliveryHouseBean>>(obj.optString("data"), object : TypeToken<List<HomeDeliveryHouseBean>>() {}.type)
                    val mHomeDeliveryBean = Gson().fromJson<HomeDeliveryBean>(dataStr, HomeDeliveryBean::class.java)
                    mHomeDeliveryBean.pickUpdetLst = resultList
                    val mSelectWaybillNumber = StringBuilder()
                    for ((index, item) in (resultList.withIndex())) {
                        if (item.isChecked) {
                            mSelectWaybillNumber.append(item.billno)
                            if (index != resultList.size - 1)
                                mSelectWaybillNumber.append(",")
                        }
                    }
                    mHomeDeliveryBean.commonStr = mSelectWaybillNumber.toString()
                    post<String>(ApiInterface.COMPELETE_HOME_DELIVERY_HOUSE_POST, getRequestBody(Gson().toJson(mHomeDeliveryBean)), object : CallBacks {
                        override fun onResult(xResult: String) {
                            mView?.fixConfiguration("")

                        }

                    })
                    /* post<String>(ApiInterface.HOME_DELIVERY_REMOVE_ITEM_GET, getRequestBody(Gson().toJson(mHomeDeliveryBean)), object : CallBacks {
                         override fun onResult(dResult: String) {
                             post<String>(ApiInterface.HOME_DELIVERY_ADD_ITEM_GET, getRequestBody(orderData), object : CallBacks {
                                 override fun onResult(xResult: String) {
                                     mView?.overOrderS()

                                 }

                             })


                         }

                     })*/
                }
            }

        })
    }
}