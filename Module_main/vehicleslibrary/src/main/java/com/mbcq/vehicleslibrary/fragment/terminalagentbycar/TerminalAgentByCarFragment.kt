package com.mbcq.vehicleslibrary.fragment.terminalagentbycar


import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.localagent.LocalAgentEvent
import com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagent.TerminalAgentEvent
import kotlinx.android.synthetic.main.fragment_terminal_agent_bycar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2018.08.25
 * 终端代理 - 按车
 */

class TerminalAgentByCarFragment : BaseSmartMVPFragment<TerminalAgentByCarContract.View, TerminalAgentByCarPresenter, TerminalAgentByCarBean>(), TerminalAgentByCarContract.View {
    var mShippingOutletsTag = ""
    var mStartDateTag = ""
    var mEndDateTag = ""
    override fun getLayoutResId(): Int = R.layout.fragment_terminal_agent_bycar

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext?.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it)

        }
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, TerminalAgentEvent::class.java).subscribe { msg ->
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
        modify_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        ARouter.getInstance().build(ARouterConstants.FixedTerminalAgentByCarActivity).withString("FixedTerminalAgentByCarJson", Gson().toJson(item)).navigation()
                        break
                    }
                }
            }

        })
        out_stock_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddTerminalAgentByCarActivity).navigation()
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
    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }

    override fun getSmartLayoutId(): Int = R.id.terminal_agent_bycar_smart

    override fun getSmartEmptyId() = R.id.terminal_agent_bycar_frame

    override fun getRecyclerViewId() = R.id.terminal_agent_bycar_recycler

    override fun setAdapter(): BaseRecyclerAdapter<TerminalAgentByCarBean> = TerminalAgentByCarAdapter(mContext).also {

    }

    override fun getPageS(list: List<TerminalAgentByCarBean>) {
        appendDatas(list)

    }

    override fun cancelS(position: Int) {
        adapter.removeItem(position)
    }
}