package com.mbcq.orderlibrary.activity.receipt.receiptinfo


import android.Manifest
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_receipt_infomation.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 15:17:58 回单详情
 */

@Route(path = ARouterConstants.ReceiptInformationActivity)
class ReceiptInformationActivity : BaseListMVPActivity<ReceiptInformationContract.View, ReceiptInformationPresenter, ReceiptInformationBean>(), ReceiptInformationContract.View {


    override fun getLayoutId(): Int = R.layout.activity_receipt_infomation
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        receipt_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun getRecyclerViewId(): Int = R.id.receipt_info_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptInformationBean> = ReceiptInformationAdapter(mContext).also {
        it.appendData(mutableListOf(
                ReceiptInformationBean("签收时间：2010-11-12\n签 收  人：吴轩\n签收网点：义乌青口", "签收", ""),
                ReceiptInformationBean("寄出时间：2010-11-12\n寄 出  人：吴轩\n寄出网点：义乌青口\n接收网点：汕头\n寄出快递公司：德邦\n快递单号：12345655558558", "寄出", ""),
                ReceiptInformationBean("返厂时间：2010-11-12\n 返 厂  人：吴轩\n 返厂网点：义乌青口", "返厂", ""),
                ReceiptInformationBean("接收时间：2010-11-12\n 接 收  人：吴轩\n 接收网点：义乌青口\n 寄出快递公司：韵达\n 快递单号：15953621552", "接收", "")
        ))
    }
}