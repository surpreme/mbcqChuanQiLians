package com.mbcq.vehicleslibrary.activity.fixhomedeliveryhouse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-27 09:22:17 修改上门提货
 */

class FixHomeDeliveryHousePresenter : BasePresenterImpl<FixHomeDeliveryHouseContract.View>(), FixHomeDeliveryHouseContract.Presenter {
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

    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 117,
    "pickUpDate": "2021-03-26T00:00:00",
    "billno": "10030005630",
    "product": "物价啊啊啊,,",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "qty": 19,
    "weight": 6.00,
    "volumn": 7.00,
    "shipper": "测试",
    "consignee": "测试1",
    "accNow": 0.00,
    "accArrived": 5666.00,
    "accBack": 0.00,
    "accMonth": 0.00,
    "isTalkGoods": 1,
    "isTalkGoodsStr": "上门提货",
    "destination": "义乌后湖",
    "accSum": 5671.00,
    "packages": "纸箱,,",
    "consigneeAddr": "收货人地址",
    "remark": "",
    "orderId": "",
    "shipperCompany": "发货人公司",
    "consigneeCompany": "收货人公司",
    "Lightandheavy": "轻货",
    "weightJs": 1750.00,
    "accGb": 5.00,
    "accBackService": 0.00,
    "accSms": 0.00,
    "accSum1": 5671.00,
    "accFetch": 0.00
    }
    ],"totalRow":
    {
    "rowCou": 1,
    "qty": 19.0,
    "weight": 6.00,
    "SUM(t.volumn)": 7.00,
    "accNow": 0.00,
    "accArrived": 5666.00,
    "accBack": 0.00,
    "accMonth": 0.00,
    "accSum": 5671.00,
    "weightJs": 1750.00,
    "accFetch": 0.00
    }
    }
     */
    override fun getLoading(id: String) {
        val params = HttpParams()
        params.put("id", id)
        get<String>(ApiInterface.HOME_DELIVERY_LOADING_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getLoadingS(Gson().fromJson<List<HomeDeliveryHouseBean>>(obj.optString("data"), object : TypeToken<List<HomeDeliveryHouseBean>>() {}.type))
                }
            }

        })
    }

    override fun removeOrder(removeOrderData: String) {
        post<String>(ApiInterface.HOME_DELIVERY_REMOVE_ITEM_GET, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderS()

            }

        })

    }

    override fun addOrder(removeOrderData: String) {
        post<String>(ApiInterface.HOME_DELIVERY_ADD_ITEM_GET, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()

            }

        })
    }

    override fun removeOrderItem(removeOrderData: String, position: Int, item: HomeDeliveryHouseBean) {
        post<String>(ApiInterface.HOME_DELIVERY_REMOVE_ITEM_GET, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position, item)

            }

        })

    }

    override fun addOrderItem(removeOrderData: String, position: Int, item: HomeDeliveryHouseBean) {
        post<String>(ApiInterface.HOME_DELIVERY_ADD_ITEM_GET, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position, item)

            }

        })
    }
}