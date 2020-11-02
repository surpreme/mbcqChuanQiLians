package com.mbcq.vehicleslibrary.fragment.arrivalshortfeederscan

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-29 14:54:32
 */

class ArrivalShortFeederScanFragmentPresenter : BasePresenterImpl<ArrivalShortFeederScanFragmentContract.View>(), ArrivalShortFeederScanFragmentContract.Presenter {
    override fun getUnLoading(selEwebidCode: String, startDate: String, endDate: String) {
        /*  val mHttpParams = HttpParams()
          mHttpParams.put("page", page)
          mHttpParams.put("limit", 15)*/
        /* mView?.getContext()?.let {
             mHttpParams.put("selWebidCode", UserInformationUtil.getWebIdCode(it))
         }*/
//         mHttpParams.put("startDate", startDate)
//         mHttpParams.put("endDate", endDate)
        val mHttpParams = HttpParams()
        mHttpParams.put("SelEwebidCode", selEwebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOADING_LOCAL_INFO_POST, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getPageS(Gson().fromJson<List<ArrivalShortFeederScanBean>>(obj.optString("data"), object : TypeToken<List<ArrivalShortFeederScanBean>>() {}.type))
            }

        })
    }

    override fun getLoading(selEwebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("SelEwebidCode", selEwebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getPageS(Gson().fromJson<List<ArrivalShortFeederScanBean>>(obj.optString("data"), object : TypeToken<List<ArrivalShortFeederScanBean>>() {}.type))
            }

        })
    }

}