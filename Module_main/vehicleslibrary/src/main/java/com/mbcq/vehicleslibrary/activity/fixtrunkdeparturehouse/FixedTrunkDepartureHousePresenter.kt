package com.mbcq.vehicleslibrary.activity.fixtrunkdeparturehouse

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 */

class FixedTrunkDepartureHousePresenter : BasePresenterImpl<FixedTrunkDepartureHouseContract.View>(), FixedTrunkDepartureHouseContract.Presenter {
    override fun modify(jsonObject: JSONObject) {
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_MODIFY_LOCAL_INFO_POST,getRequestBody(jsonObject),object:CallBacks{
            override fun onResult(result: String) {
                mView?.modifyS()
            }

        })
    }

}