package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.byorder


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.localagent.LocalAgentEvent
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.bycar.LocalGentByCarAdapter
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.bycar.LocalGentByCarBean
import kotlinx.android.synthetic.main.fragment_locala_gent_bycar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-12-30 14:19:34 本地代理 按票
 */

class LocalGentByOrderFragment : BaseSmartMVPFragment<LocalGentByOrderContract.View, LocalGentByOrderPresenter, LocalGentByOrderBean>(), LocalGentByOrderContract.View {
    var mShippingOutletsTag = ""
    var mStartDateTag = ""
    var mEndDateTag = ""

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it)

        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_local_gent_by_order

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, LocalAgentEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }
    }

    override fun onClick() {
        super.onClick()
        out_stock_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddLocalGentShortFeederActivity).navigation()
            }

        })
        modify_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        ARouter.getInstance().build(ARouterConstants.FixedLocalGentShortFeederHouseActivity).withString("FixedLocalGentShortFeederJson", Gson().toJson(item)).navigation()
                        break
                    }
                }
            }

        })

        cancel_transfer_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for ((index, item) in (adapter.getAllData()).withIndex()) {
                    if (item.isChecked) {
                        activity?.let {
                            TalkSureCancelDialog(it, getScreenWidth(), "您确定要取消${item.agentBillno}吗？") {
                                mPresenter?.cancel(item, index)

                            }.show()
                        }
                        break
                    }
                }
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

    override fun getSmartLayoutId(): Int = R.id.local_short_feeder_smart
    override fun getSmartEmptyId(): Int = R.id.local_short_feeder_smart_frame
    override fun getRecyclerViewId(): Int = R.id.local_short_feeder_recycler

    override fun setAdapter(): BaseRecyclerAdapter<LocalGentByOrderBean> = LocalGentByOrderAdapter(mContext)
    override fun getPageS(list: List<LocalGentByOrderBean>) {
        appendDatas(list)

    }

    override fun cancelS(position: Int) {
        adapter.notifyItemRemoved(position)

    }
}