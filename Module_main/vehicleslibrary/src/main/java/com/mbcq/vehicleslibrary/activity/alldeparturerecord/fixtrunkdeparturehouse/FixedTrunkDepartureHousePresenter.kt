package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 */

class FixedTrunkDepartureHousePresenter : BasePresenterImpl<FixedTrunkDepartureHouseContract.View>(), FixedTrunkDepartureHouseContract.Presenter {
    override fun getCarInfo(id: Int, inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("id", id)
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                val mTotalData = obj.optJSONArray("data")
                var mFixedTrunkDepartureHouseInfo: FixedTrunkDepartureHouseInfo? = null
                mTotalData?.let { it1 ->
                    if (!it1.isNull(0)) {
                        val mFirstJson = it1.getJSONObject(0)
                        val mFirstData = mFirstJson.optJSONArray("data")
                        mFirstData?.let {
                            val mCarInfo = mFirstData.optString(0)
                            mFixedTrunkDepartureHouseInfo = Gson().fromJson(mCarInfo, FixedTrunkDepartureHouseInfo::class.java)
                        }

                    }
                    if (!it1.isNull(1)) {
                        val mSencondJson = it1.getJSONObject(1)
                        val mSecondData = mSencondJson.optString("data")
                        mFixedTrunkDepartureHouseInfo?.item = Gson().fromJson<List<StockWaybillListBean>>(mSecondData, object : TypeToken<List<StockWaybillListBean>>() {}.type)

                    }
                    mFixedTrunkDepartureHouseInfo?.let {
                        mView?.getCarInfo(it)
                    }
                }
            }

        })
    }

    override fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderS()

            }

        })

    }

    override fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean) {
        val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position, item)

            }

        })
    }

    override fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean) {
        val mAddTrunkOrderDataBean = AddTrunkOrderDataBean()
        mAddTrunkOrderDataBean.commonStr = commonStr
        mAddTrunkOrderDataBean.id = id
        mAddTrunkOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        val gxVehicleDetLst = mutableListOf<StockWaybillListBean>()
        gxVehicleDetLst.add(item)
        mAddTrunkOrderDataBean.gxVehicleDetLst = gxVehicleDetLst
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddTrunkOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position, item)

            }

        })
    }

    override fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, gxVehicleDetLst: List<StockWaybillListBean>) {
        val mAddTrunkOrderDataBean = AddTrunkOrderDataBean()
        mAddTrunkOrderDataBean.commonStr = commonStr
        mAddTrunkOrderDataBean.id = id
        mAddTrunkOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        mAddTrunkOrderDataBean.gxVehicleDetLst = gxVehicleDetLst

        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddTrunkOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()

            }

        })
    }

    override fun modify(jsonObject: JSONObject) {
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_MODIFY_LOCAL_INFO_POST, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                mView?.modifyS()
            }

        })
    }

    override fun getInventory(page: Int) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        get<String>(ApiInterface.WAYBILL_INVENTORY_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                mView?.getInventoryS(Gson().fromJson<List<StockWaybillListBean>>(obj.optString("data"), object : TypeToken<List<StockWaybillListBean>>() {}.type))

            }

        })
    }

    override fun invalidOrder(inoneVehicleFlag: String, id: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS()

            }

        })
    }

    override fun completeCar(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeCarS()

            }

        })
    }

}