package com.mbcq.orderlibrary.activity.addshipper


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_add_shipper.*

/**
 * @author: lzy
 * @time: 2020.09.24
 */
@Route(path = ARouterConstants.AddShipperActivity)
class AddShipperActivity : BaseMVPActivity<AddShipperContract.View, AddShipperPresenter>(), AddShipperContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_shipper
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        /**
         * 监听editttext焦点
         */
        phone_ed.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (phone_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getShipperInfo(phone_ed.text.toString())
                }
            }
        }
    }

    override fun onClick() {
        super.onClick()
        add_shipper_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

    override fun getShipperInfoS(result: String) {

    }
}