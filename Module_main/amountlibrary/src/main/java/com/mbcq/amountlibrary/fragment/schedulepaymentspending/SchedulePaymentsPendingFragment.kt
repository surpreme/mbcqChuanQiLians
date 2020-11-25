package com.mbcq.amountlibrary.fragment.schedulepaymentspending


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.fragment_schedule_payments_pending.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-20 16:32:45 待发款明细表
 */

class SchedulePaymentsPendingFragment : BaseSmartMVPFragment<SchedulePaymentsPendingContract.View, SchedulePaymentsPendingPresenter, SchedulePaymentsPendingBean>(), SchedulePaymentsPendingContract.View {

    override fun getLayoutResId(): Int = R.layout.fragment_schedule_payments_pending


    override fun getSmartLayoutId(): Int = R.id.schedule_payment_spending_smart

    override fun getSmartEmptyId(): Int = R.id.schedule_payment_spending_smart_frame

    override fun getRecyclerViewId(): Int = R.id.schedule_payment_spending_recycler

    override fun setAdapter(): BaseRecyclerAdapter<SchedulePaymentsPendingBean> = SchedulePaymentsPendingAdapter(mContext).also {
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)
    }

    override fun onClick() {
        super.onClick()
        generate_payment_voucher_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mSlectList = mutableListOf<SchedulePaymentsPendingBean>()
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        mSlectList.add(item)
                    }
                }
                val mGeneratePaymentVoucherListBean = GeneratePaymentVoucherListBean()
                mGeneratePaymentVoucherListBean.list = mSlectList
                ARouter.getInstance().build(ARouterConstants.GeneratePaymentVoucherActivity).withSerializable("GeneratePaymentVoucherList", mGeneratePaymentVoucherListBean).navigation()

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

    override fun getPageS(list: List<SchedulePaymentsPendingBean>) {
        appendDatas(list)
    }

}