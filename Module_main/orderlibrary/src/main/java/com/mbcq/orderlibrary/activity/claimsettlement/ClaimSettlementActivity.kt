package com.mbcq.orderlibrary.activity.claimsettlement


import com.alibaba.android.arouter.launcher.ARouter


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_claim_settlement.*

/**
 * @author: lzy
 * @time: 2020-10-28 15:29:45 理赔记录
 */

@Route(path = ARouterConstants.ClaimSettlementActivity)
class ClaimSettlementActivity : BaseSmartMVPActivity<ClaimSettlementContract.View, ClaimSettlementPresenter, ClaimSettlementBean>(), ClaimSettlementContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""
    override fun getLayoutId(): Int = R.layout.activity_claim_settlement
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        add_claim_settlement_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddClaimSettlementActivity).navigation()
            }

        })
        claim_settlement_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "到货库存记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
//                                refresh()

                            }

                        }).show(supportFragmentManager, "ClaimSettlementActivityFilterWithTimeDialog")
                    }

                })
            }

        })
        claim_settlement_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
    }
    override fun getSmartLayoutId() = R.id.claim_settlement_smart
    override fun getSmartEmptyId() = R.id.claim_settlement_smart_frame
    override fun getRecyclerViewId(): Int = R.id.claim_settlement_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ClaimSettlementBean> = ClaimSettlementAdapter(mContext).also {
        it.appendData(mutableListOf(ClaimSettlementBean()))
    }
}