package com.mbcq.orderlibrary.activity.claimsettlement


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_claim_settlement.*

/**
 * @author: lzy
 * @time: 2020-10-28 15:29:45 理赔 记录
 */

@Route(path = ARouterConstants.ClaimSettlementActivity)
class ClaimSettlementActivity : BaseMVPActivity<ClaimSettlementContract.View, ClaimSettlementPresenter>(), ClaimSettlementContract.View {
    override fun getLayoutId(): Int = R.layout.activity_claim_settlement
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        pencil_big_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setSize(ScreenSizeUtils.dip2px(mContext, 15f));


            }

        })
        pencil_center_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setSize(ScreenSizeUtils.dip2px(mContext, 10f));


            }

        })
        pencil_small_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setSize(ScreenSizeUtils.dip2px(mContext, 5f));


            }

        })
        pencil_blue_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setColor("#03A9F4");

            }

        })
        pencil_orange_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setColor("#FFC107");

            }

        })
        pencil_purple_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.setColor("#A17CE1");

            }

        })

        revoke_btn.setOnClickListener(object : SingleClick(3000) {
            override fun onSingleClick(v: View?) {
                mDoodleView.back()
            }

        })
        clear_btn.setOnClickListener(object : SingleClick(3000) {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要清除吗？") {
                    mDoodleView.reset()

                }.show()
            }

        })
        claim_settlement_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}