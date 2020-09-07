package com.mbcq.orderlibrary.activity.addshipper

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.ResultDataCallBack
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2020.09.24
 */

class AddShipperPresenter : BasePresenterImpl<AddShipperContract.View>(), AddShipperContract.Presenter {
    override fun getShipperInfo(contactMb: String) {
        val params = HttpParams()
        params.put("contactMb", contactMb)
        get<String>(ApiInterface.ACCEPT_SELECT_SHIPPER_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getShipperInfoS(result)
            }

        })
    }

}