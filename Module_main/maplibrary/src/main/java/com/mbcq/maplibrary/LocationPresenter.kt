package com.mbcq.maplibrary

import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.maplibrary.view.WebCodeLocationBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class LocationPresenter : BasePresenterImpl<LocationContract.View>(), LocationContract.Presenter {
    /**
     * {"code":0,"msg":"","count":8,"data":[
    {
    "id": 1,
    "companyId": 2001,
    "webid": "义乌后湖",
    "webidCode": 1001,
    "webSimplicity": "ywhh",
    "webidType": 1,
    "webidTypeStr": "子公司",
    "parWebCod": 9,
    "parWebCodStr": null,
    "webMb": "15995675862",
    "webTel": "021-12345678",
    "province": "北京",
    "city": "北京市",
    "county": "东城区",
    "street": "东华门街道",
    "address": "23",
    "longitude": "121.295114",
    "latitude": "31.160045",
    "dbWebCode": "1010,1006,1007",
    "dbWebCodeStr": "永康中田,汕头峡山,杭州萧山",
    "gxWebCode": "1010,1006,1007",
    "gxWebCodeStr": "永康中田,汕头峡山,杭州萧山",
    "xzqhbm": "23",
    "isValid": 1,
    "isValidStr": "否",
    "opeMan": "汕头",
    "recordDate": "2018-12-03T00:00:00",
    "comNam": "test2"
    }
    ]}
     */
    override fun getAllWebCodeInfo(mLatLng: LatLng) {
        val params = HttpParams()
        params.put("limit", 1000)
        params.put("page", 1)
        get<String>(ApiInterface.ACCEPT_OUTLET_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getAllWebCodeInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<WebCodeLocationBean>>() {}.type),mLatLng)

                }
            }

        })
    }

}