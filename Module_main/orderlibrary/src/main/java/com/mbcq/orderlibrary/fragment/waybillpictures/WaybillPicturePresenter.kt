package com.mbcq.orderlibrary.fragment.waybillpictures

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-26 09:32:02
 */

class WaybillPicturePresenter : BasePresenterImpl<WaybillPictureContract.View>(), WaybillPictureContract.Presenter {
    /**
     * {"code":0,"msg":"","count":5,"data":[
    {
    "billno": "10030004823",
    "imgtype": 16,
    "imgtypestr": "异常登记",
    "imgsize": 2.30,
    "uploaddate": "2020-12-08T11:02:40",
    "uploadman": "",
    "imgpath": "/Content/Images/images/allimages/badwaybill/20201207_205854.jpg",
    "imgname": "20201207_205854.jpg",
    "imageid": "1"
    }
    ]}
     */
    override fun getPictures(billno: String) {
        val parmas = HttpParams()
        parmas.put("Billno", billno)
        get<String>(ApiInterface.DOWNLOAD_PICTURE_GET, parmas, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getPictureS(Gson().fromJson(JSONObject(result).optString("data"), object : TypeToken<List<WaybillPictureBean>>() {}.type))

            }

        })
    }

}