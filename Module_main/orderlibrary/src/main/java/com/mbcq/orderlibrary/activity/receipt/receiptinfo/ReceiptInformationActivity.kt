package com.mbcq.orderlibrary.activity.receipt.receiptinfo


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_receipt_infomation.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 15:17:58 回单详情
 */

@Route(path = ARouterConstants.ReceiptInformationActivity)
class ReceiptInformationActivity : BaseListMVPActivity<ReceiptInformationContract.View, ReceiptInformationPresenter, ReceiptInformationBean>(), ReceiptInformationContract.View {
    @Autowired(name = "billno")
    @JvmField
    var mXBillno: String = ""

    override fun getLayoutId(): Int = R.layout.activity_receipt_infomation

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWayBillInfo(mXBillno)
        mPresenter?.getReceiptInfo(mXBillno)
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

    }

    @SuppressLint("SetTextI18n")
    override fun getWaybillDetailS(data: JSONObject) {
        waybill_number_tv.text = data.optString("billno")
        waybill_time_tv.text = data.optString("billDate")
        shipper_outlets_tv.text = data.optString("webidCodeStr")
        receiver_outlets_tv.text = data.optString("ewebidCodeStr")
        goods_info_tv.text = "${data.optString("product")}  ${data.optString("totalQty")}  ${data.optString("packages")}"
        shipper_tv.text = data.optString("shipper")
        receiver_tv.text = data.optString("consignee")

    }

    override fun getWaybillDetailNull() {
        TalkSureDialog(mContext, getScreenWidth(), "运单信息不存在或异常，请核实后重试").show()

    }

    override fun getReceiptInfoS(list: List<ReceiptInformationBean>, mStateList: List<Boolean>) {
        adapter.appendData(list)
        for ((index, mState) in mStateList.withIndex()) {
            when (index) {
                0 -> {//签收
                    sign_for_state_tv.text = if (mState) "已签收" else "未签收"
                    sign_for_state_tv.setTextColor(resources.getColor(if (mState) R.color.base_green else R.color.black))
                }
                1 -> {//寄出
                    send_out_state_tv.text = if (mState) "已寄出" else "待寄出"
                    send_out_state_tv.setTextColor(resources.getColor(if (mState) R.color.base_green else R.color.black))
                }
                2 -> {//接收
                    receive_state_tv.text = if (mState) "已接收" else "待接收"
                    receive_state_tv.setTextColor(resources.getColor(if (mState) R.color.base_green else R.color.black))
                }
                3 -> {//返厂
                    return_factory_state_tv.text = if (mState) "已返厂" else "待返厂"
                    return_factory_state_tv.setTextColor(resources.getColor(if (mState) R.color.base_green else R.color.black))
                }
            }

        }
    }
}