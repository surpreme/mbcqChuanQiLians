package com.mbcq.orderlibrary.fragment.waybillpictures

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2020-10-26 09:32:02
 */

class WaybillPicturePresenter : BasePresenterImpl<WaybillPictureContract.View>(), WaybillPictureContract.Presenter {
    override fun getPictures(billno: String) {
        val parmas = HttpParams()
        parmas.put("Billno", billno)
        get<String>(ApiInterface.DOWNLOAD_PICTURE_GET,parmas,object:CallBacks{
            override fun onResult(result: String) {

            }

        })
    }

}