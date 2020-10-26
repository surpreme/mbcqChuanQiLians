package com.mbcq.orderlibrary.fragment.waybillpictures


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-26 09:32:02
 */

class WaybillPictureFragment : BaseMVPFragment<WaybillPictureContract.View, WaybillPicturePresenter>(), WaybillPictureContract.View {
    var WaybillPicture = ""

    override fun getLayoutResId(): Int = R.layout.fragment_waybill_picture
    override fun initViews(view: View) {

    }

    override fun initExtra() {
        super.initExtra()
        arguments?.let {
            WaybillPicture = it.getString("WaybillDetails", "{}")
        }
    }

    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(WaybillPicture)
        mPresenter?.getPictures(obj.optString("billno"))
    }
}