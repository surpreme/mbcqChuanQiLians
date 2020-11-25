package com.mbcq.amountlibrary.activity.generatepaymentvoucher


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.fragment.schedulepaymentspending.GeneratePaymentVoucherListBean
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.ImageViewAdapter
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import kotlinx.android.synthetic.main.activity_generate_payment_voucher.*
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2020-11-20 13:10:12 生成付款凭证
 */

@Route(path = ARouterConstants.GeneratePaymentVoucherActivity)
class GeneratePaymentVoucherActivity : BaseMVPActivity<GeneratePaymentVoucherContract.View, GeneratePaymentVoucherPresenter>(), GeneratePaymentVoucherContract.View {
    @Autowired(name = "GeneratePaymentVoucherList")
    @JvmField
    var mGeneratePaymentVoucherListBean: GeneratePaymentVoucherListBean? = null

    override fun getLayoutId(): Int = R.layout.activity_generate_payment_voucher
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    @SuppressLint("RtlHardcoded")
    override fun initDatas() {
        super.initDatas()
        mGeneratePaymentVoucherListBean?.list?.let {
            if (it.isNotEmpty()) {
                val mBaseTextAdapterBeanList = mutableListOf<BaseTextAdapterBean>()
                for (item in it) {
                    val mBaseTextAdapterBean = BaseTextAdapterBean()
                    mBaseTextAdapterBean.title = "原单号：${item.billno}   金额：${item.accDaiShou}\n 摘要：${item.remark}"
                    mBaseTextAdapterBean.tag = Gson().toJson(item)
                    mBaseTextAdapterBeanList.add(mBaseTextAdapterBean)
                }
                val mTextViewAdapter = TextViewAdapter<BaseTextAdapterBean>(mContext, Gravity.LEFT)
                generate_payment_voucher_recycler.layoutManager = LinearLayoutManager(mContext)
                mTextViewAdapter.setIsShowOutSide(false)
                generate_payment_voucher_recycler.adapter = mTextViewAdapter
                mTextViewAdapter.appendData(mBaseTextAdapterBeanList)
            }

        }
    }

    override fun onClick() {
        super.onClick()
        voucher_date_ll.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View?) {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    voucher_date_tv.text = format

                }, "选择凭证日期", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = false).show(voucher_date_ll)
            }

        })
        generate_payment_voucher_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}