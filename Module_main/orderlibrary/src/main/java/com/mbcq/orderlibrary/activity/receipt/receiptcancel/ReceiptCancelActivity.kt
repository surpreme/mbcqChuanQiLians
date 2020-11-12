package com.mbcq.orderlibrary.activity.receipt.receiptcancel


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_receipt_cancel.*

/**
 * @author: lzy
 * @time: 2020-11-12 15:04:02 回单取消
 */

@Route(path = ARouterConstants.ReceiptCancelActivity)
class ReceiptCancelActivity : BaseListMVPActivity<ReceiptCancelContract.View, ReceiptCancelPresenter, ReceiptCancelBean>(), ReceiptCancelContract.View {
    override fun getLayoutId(): Int = R.layout.activity_receipt_cancel
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    fun searchInfo() {
        if (billno_ed.text.toString().isNotBlank()) {
            mPresenter?.searchOrder(billno_ed.text.toString())
        } else {
            showToast("请检查您的运单号")
        }
    }

    override fun onClick() {
        super.onClick()
        receipt_cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mAdapterData = adapter.getAllData()
                if (mAdapterData.isEmpty()) {
                    showToast("请检查您的运单信息后重试")
                    return
                }
                mPresenter?.deleteOrderState(mAdapterData[mAdapterData.lastIndex].cztype, mAdapterData[mAdapterData.lastIndex].id.toString(), mAdapterData.lastIndex)
            }

        })
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                searchInfo()
            }

        })
        receipt_cancel_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.receipt_cancel_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ReceiptCancelBean> = ReceiptCancelAdapter(mContext)
    override fun searchOrderS(list: List<ReceiptCancelBean>) {
        if (adapter.getAllData().isNotEmpty()) {
            adapter.clearData()
        }
        adapter.appendData(list)
    }

    override fun deleteOrderStateS(position: Int, result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "取消成功，您可以继续取消或者退出！") {
            searchInfo()
        }.show()

    }
}