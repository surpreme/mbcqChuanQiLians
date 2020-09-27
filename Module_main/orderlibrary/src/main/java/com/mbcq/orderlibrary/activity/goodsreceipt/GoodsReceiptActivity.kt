package com.mbcq.orderlibrary.activity.goodsreceipt


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_goods_receipt.*

/**
 * @author: lzy
 * @time: 2020-09-27 11:18:00
 */
@Route(path = ARouterConstants.GoodsReceiptActivity)
class GoodsReceiptActivity : BaseMVPActivity<GoodsReceiptContract.View, GoodsReceiptPresenter>(), GoodsReceiptContract.View {
    override fun getLayoutId(): Int = R.layout.activity_goods_receipt
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        goods_receipt_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

}