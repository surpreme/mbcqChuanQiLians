package com.mbcq.amountlibrary.fragment.paymentconfirmation


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.fragment.schedulepaymentspending.SchedulePaymentsPendingAdapter
import com.mbcq.amountlibrary.fragment.schedulepaymentspending.SchedulePaymentsPendingBean
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

/**
 * @author: lzy
 * @time: 2020-11-20 16:29:12 贷款回款确认
 */

class PaymentConfirmationFragment : BaseSmartMVPFragment<PaymentConfirmationContract.View, PaymentConfirmationPresenter, PaymentConfirmationBean>(), PaymentConfirmationContract.View {


    override fun getLayoutResId(): Int = R.layout.fragment_payment_confirmation

    override fun getSmartLayoutId(): Int = R.id.payment_confirmation_smart

    override fun getSmartEmptyId(): Int = R.id.payment_confirmation_smart_frame

    override fun getRecyclerViewId(): Int = R.id.payment_confirmation_recycler

    override fun setAdapter(): BaseRecyclerAdapter<PaymentConfirmationBean> = PaymentConfirmationAdapter(mContext).also {
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)

    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getPageS(list: List<PaymentConfirmationBean>) {
        appendDatas(list)
    }
}