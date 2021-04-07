package com.mbcq.orderlibrary.activity.fixreceiver

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-04-07 17:46:43 修改收货客户
 */

class FixReceiverPresenter : BasePresenterImpl<FixReceiverContract.View>(), FixReceiverContract.Presenter {
    override fun fixData(paramsObj: JSONObject) {
        post<String>(ApiInterface.CONSIGNEE_FIXED_INFO_POST, getRequestBody(paramsObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.fixDataS(result)


            }

        })
    }

}