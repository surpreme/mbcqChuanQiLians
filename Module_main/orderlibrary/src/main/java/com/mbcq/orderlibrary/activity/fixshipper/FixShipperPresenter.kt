package com.mbcq.orderlibrary.activity.fixshipper

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-04-07 14:41:12 修改发货客户
 */

class FixShipperPresenter : BasePresenterImpl<FixShipperContract.View>(), FixShipperContract.Presenter {
    override fun fixData(paramsObj: JSONObject) {
        post<String>(ApiInterface.SHIPPER_FIXED_INFO_POST, getRequestBody(paramsObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.fixDataS(result)


            }

        })

    }

}