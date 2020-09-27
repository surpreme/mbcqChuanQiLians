package com.mbcq.orderlibrary.activity.goodsreceipt


import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_goods_receipt.*

/**
 * @author: lzy
 * @time: 2020-09-27 11:18:00
 *
 */
@Route(path = ARouterConstants.GoodsReceiptActivity)
class GoodsReceiptActivity : BaseListMVPActivity<GoodsReceiptContract.View, GoodsReceiptPresenter, GoodsReceiptBean>(), GoodsReceiptContract.View {
    override fun getLayoutId(): Int = R.layout.activity_goods_receipt
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getPage(1)
    }

    override fun onClick() {
        super.onClick()
        goods_receipt_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }


    }

    override fun getRecyclerViewId(): Int = R.id.goods_receipt_recycler

    override fun setAdapter(): BaseRecyclerAdapter<GoodsReceiptBean> = GoodsReceiptAdapter(mContext)
    override fun getPageS(list: List<GoodsReceiptBean>) {
        adapter.appendData(list)
    }

}