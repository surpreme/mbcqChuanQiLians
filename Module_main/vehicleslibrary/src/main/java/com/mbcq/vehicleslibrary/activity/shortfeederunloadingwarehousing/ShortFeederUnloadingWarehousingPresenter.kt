package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 */

class ShortFeederUnloadingWarehousingPresenter : BasePresenterImpl<ShortFeederUnloadingWarehousingContract.View>(), ShortFeederUnloadingWarehousingContract.Presenter {
    override fun getVehicleInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {

             /*   val obj = JSONObject(result)
                val mTotalData = obj.optJSONArray("data")
                var mFixShortFeederHouseCarInfo: FixShortFeederHouseCarInfo? = null
                mTotalData?.let { it1 ->
                    if (!it1.isNull(0)) {
                        val mFirstJson = it1.getJSONObject(0)
                        val mFirstData = mFirstJson.optJSONArray("data")
                        mFirstData?.let {
                            val mCarInfo = mFirstData.optString(0)
                            mFixShortFeederHouseCarInfo = Gson().fromJson(mCarInfo, FixShortFeederHouseCarInfo::class.java)
                        }

                    }
                    if (!it1.isNull(1)) {
                        val mSencondJson = it1.getJSONObject(1)
                        val mSecondData = mSencondJson.optString("data")
                        mFixShortFeederHouseCarInfo?.item = Gson().fromJson<List<StockWaybillListBean>>(mSecondData, object : TypeToken<List<StockWaybillListBean>>() {}.type)

                    }
                    mFixShortFeederHouseCarInfo?.let {
                        mView?.getCarInfo(it)
                    }
                }*/
            }

        })
    }

}