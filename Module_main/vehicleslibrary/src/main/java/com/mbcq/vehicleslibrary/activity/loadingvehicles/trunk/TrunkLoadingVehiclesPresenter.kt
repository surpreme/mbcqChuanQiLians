package com.mbcq.vehicleslibrary.activity.loadingvehicles.trunk

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.loadingvehicles.LoadingVehiclesBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-08 11:24:30 干线有计划装车
 */

class TrunkLoadingVehiclesPresenter : BasePresenterImpl<TrunkLoadingVehiclesContract.View>(), TrunkLoadingVehiclesContract.Presenter {
    /**
     * 发车记录短驳和干线
     */
    override fun getScanVehicleList(startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        params.put("vehicleState", 0)//发车计划中 发货 到车等
        params.put("VehicleStateStr", 0)//发车计划中
        params.put("IsScanStr", "1")//筛选状态的种类 有计划无计划"1,2"
        params.put("startDate", startDate)
        params.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val mXobj = JSONObject(result)
                mXobj.optJSONArray("data")?.let {
                    val data = mutableListOf<LoadingVehiclesBean>()
                    val mXlist = Gson().fromJson<List<LoadingVehiclesBean>>(mXobj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                    for (item in mXlist) {
                        item.type = 1
                        data.add(item)
                    }
                    mView?.getScanVehicleListS(data)
                }

            }

        })
    }


    override fun searchTrunkDeparture(inoneVehicleFlag: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("InoneVehicleFlag", inoneVehicleFlag)
        mHttpParams.put("IsScanStr", "1")//筛选状态的种类 有计划无计划"1,2"
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                val data = mutableListOf<LoadingVehiclesBean>()
                for (item in list) {
                    item.type = 1
                    data.add(item)
                }
                mView?.getScanVehicleListS(data)
            }

        })
    }

    fun getSearchList(result: String): List<LoadingVehiclesBean> {
        return Gson().fromJson<List<LoadingVehiclesBean>>(JSONObject(result).optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
    }

    fun getSearchResultList(result: String): List<LoadingVehiclesBean> {
        val data = mutableListOf<LoadingVehiclesBean>()
        for (item in Gson().fromJson<List<LoadingVehiclesBean>>(JSONObject(result).optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)) {
            item.type = 1
            data.add(item)
        }
        return data
    }

    fun searchInByBillNo(inoneVehicleFlag: String, isShort: Boolean) {
        if (isShort) {
            showError("请到干线有计划查看")
//            searchShortFeeder(inoneVehicleFlag)
        } else {
            searchTrunkDeparture(inoneVehicleFlag)
        }

    }

    override fun searchScanInfo(sendInfo: String) {
        val params = HttpParams()
        if (checkStrIsNum(sendInfo)) {
            params.put("billno", sendInfo)
            get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_BILLNO_GET, params, object : CallBacks {
                override fun onResult(result: String) {
                    if (getSearchList(result).isEmpty()) {


                    } else
                        searchInByBillNo(getSearchResultList(result)[0].inoneVehicleFlag, false)

                }

            })
        } else {
            params.put("InoneVehicleFlag", sendInfo)
            params.put("IsScanStr", "1")//筛选状态的种类 "1,2"有计划无计划

            get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, params, object : CallBacks {
                override fun onResult(result: String) {

                    if (getSearchList(result).isEmpty()) {

                    } else {
                        mView?.searchScanInfoS(getSearchResultList(result))
                    }

                }

            })
        }


    }

    /**
     * @1短驳
     * @2干线
     */

    override fun invalidOrder(inoneVehicleFlag: String, id: Int, mType: Int, position: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS(position)

            }

        })
    }

    override fun saveScanPost(id: Int, inoneVehicleFlag: String, mType: Int, position: Int) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveScanPostS(position)

            }

        })
    }
}