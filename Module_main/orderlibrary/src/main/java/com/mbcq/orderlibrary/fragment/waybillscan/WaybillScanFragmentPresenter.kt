package com.mbcq.orderlibrary.fragment.waybillscan

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-29 14:43:07 运单扫描轨迹
 */

class WaybillScanFragmentPresenter : BasePresenterImpl<WaybillScanFragmentContract.View>(), WaybillScanFragmentContract.Presenter {
    override fun getWaybillScan(billno: String) {
        val params = HttpParams()
        if (billno.isNotBlank())
            params.put("billno", billno)
//        params.put("inOneVehicleFlag", inOneVehicleFlag)
        params.put("limit", 9999)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        params.put("scanOpeType", "-1")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    mView?.getWaybillScanS(Gson().fromJson(obj.optString("data"),object: TypeToken<List<WaybillScanFragmentListBean>>() {}.type))
                }

            }

        })
    }

}