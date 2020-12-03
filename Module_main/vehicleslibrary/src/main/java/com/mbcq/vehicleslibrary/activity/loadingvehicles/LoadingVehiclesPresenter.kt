package com.mbcq.vehicleslibrary.activity.loadingvehicles

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import org.json.JSONObject
import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author: lzy
 * @time: 2020-11-04 09:49:32 装车
 */

class LoadingVehiclesPresenter : BasePresenterImpl<LoadingVehiclesContract.View>(), LoadingVehiclesContract.Presenter {
    override fun getShortFeeder(startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        params.put("vehicleState", 0)//发车计划中
//        params.put("VehicleStateStr", 0)//发车计划中
//        params.put("IsScan", 1)//是否扫描
        params.put("CommonStr", "1,2")//是否扫描
        params.put("startDate", startDate)
        params.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                    val data = mutableListOf<LoadingVehiclesBean>()
                    for (item in list) {
                        item.type = 0
                        data.add(item)
                    }
                    mView?.getShortFeederS(data, false, isCanRefresh = false)
                }

            }

        })
    }


    @SuppressLint("SimpleDateFormat")
    override fun searchShortFeeder(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val mDate = Date(System.currentTimeMillis())
        val format = mDateFormat.format(mDate)
        val mStartDateTag = "$format 00:00:00"
        val mEndDateTag = "$format 23:59:59"
        params.put("startDate", mStartDateTag)
        params.put("endDate", mEndDateTag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                    val data = mutableListOf<LoadingVehiclesBean>()
                    for (item in list) {
                        item.type = 0
                        data.add(item)
                    }
                    if (data.isEmpty()) {
                        searchTrunkDeparture(inoneVehicleFlag)
                    } else
                        mView?.getShortFeederS(data, true, isCanRefresh = true)
                }

            }

        })
    }

    override fun getTrunkDeparture(startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("page", 1)
        mHttpParams.put("limit", 1000)
        mHttpParams.put("vehicleState", 0)//发车计划中
        mHttpParams.put("CommonStr", 0)//发车计划中
        mHttpParams.put("VehicleStateStr", 0)//发车计划中
        mHttpParams.put("IsScan", 1)//是否扫描
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                val data = mutableListOf<LoadingVehiclesBean>()
                for (item in list) {
                    item.type = 1
                    data.add(item)
                }
                mView?.getTrunkDepartureS(data, false, isCanRefresh = false)
            }

        })
    }

    override fun searchTrunkDeparture(inoneVehicleFlag: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                val data = mutableListOf<LoadingVehiclesBean>()
                for (item in list) {
                    item.type = 1
                    data.add(item)
                }
                mView?.getTrunkDepartureS(data, true, isCanRefresh = true)
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
        if (isShort){
            searchShortFeeder(inoneVehicleFlag)
        }else{
            searchTrunkDeparture(inoneVehicleFlag)
        }

    }

    override fun searchScanInfo(sendInfo: String) {
        val params = HttpParams()
        if (checkStrIsNum(sendInfo)) {
            params.put("billno", sendInfo)
            get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_BILLNO_GET, params, object : CallBacks {
                override fun onResult(result: String) {
                    if (getSearchList(result).isEmpty()) {

                        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_BILLNO_GET, params, object : CallBacks {
                            override fun onResult(result: String) {
                                if (getSearchList(result).isEmpty()) {


                                } else
                                    searchInByBillNo(getSearchResultList(result)[0].inoneVehicleFlag, false)

                            }

                        })
                    } else
                        searchInByBillNo(getSearchResultList(result)[0].inoneVehicleFlag, true)

                }

            })
        } else {
            params.put("InoneVehicleFlag", sendInfo)
            get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, params, object : CallBacks {
                override fun onResult(result: String) {

                    if (getSearchList(result).isEmpty()) {
                        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
                            override fun onResult(result: String) {
                                if (getSearchList(result).isEmpty()) {


                                } else
                                    mView?.searchScanInfoS(getSearchResultList(result))

                            }

                        })

                    } else {
                        mView?.searchScanInfoS(getSearchResultList(result))
                    }

                }

            })
        }


    }

}