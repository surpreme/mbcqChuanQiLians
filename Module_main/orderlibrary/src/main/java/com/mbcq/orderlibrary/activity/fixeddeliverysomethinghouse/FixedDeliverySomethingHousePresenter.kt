package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.deliverysomethinghouse.DeliverySomethingHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-09 11:00:00 修改送货上门
 */

class FixedDeliverySomethingHousePresenter : BasePresenterImpl<FixedDeliverySomethingHouseContract.View>(), FixedDeliverySomethingHouseContract.Presenter {
    override fun getInventory() {
        get<String>(ApiInterface.DELIVERY_SOMETHING_STOCK_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInventoryS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<FixedDeliverySomethingHouseBean>>() {}.type))
                }

            }

        })
    }

    override fun getLoadingInventory(id: String, sendInOneFlag: String) {
        val params = HttpParams()
        params.put("Id", id)
        params.put("SendInOneFlag", sendInOneFlag)
        get<String>(ApiInterface.DELIVERY_SOMETHING_LOADING_STOCK_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getLoadingInventory(Gson().fromJson(obj.optString("data"), object : TypeToken<List<FixedDeliverySomethingHouseBean>>() {}.type))
                }

            }

        })
    }

    override fun addOrderItem(removeOrderData: String, position: Int, item: FixedDeliverySomethingHouseBean) {
        post<String>(ApiInterface.DELIVERY_SOMETHING_ADD_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position, item)

            }

        })
    }

    override fun addOrder(removeOrderData: String) {
        post<String>(ApiInterface.DELIVERY_SOMETHING_ADD_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()

            }

        })
    }

    override fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: FixedDeliverySomethingHouseBean) {
      /*  val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)*/
        post<String>(ApiInterface.DELIVERY_SOMETHING_REMOVE_POST, getRequestBody(commonStr), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position, item)
            }
        })
    }
     override fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("sendInOneFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DELIVERY_SOMETHING_REMOVE_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderS()
            }
        })
    }

}