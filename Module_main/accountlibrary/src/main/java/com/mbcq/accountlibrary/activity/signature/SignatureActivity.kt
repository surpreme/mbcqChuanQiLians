package com.mbcq.accountlibrary.activity.signature

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.FileUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_signature.*

/**
 * @time 2020-10-29 09:57:00 签名 
 * @author lzy
 */
@Route(path = ARouterConstants.SignatureActivity)
class SignatureActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_signature
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        signature_save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDoodleView.bitmap.let {
                    signature_show_iv.setImageBitmap(it)
                    val resultFile = FileUtils.getFile(it, "mbcq")

                }

//                mDoodleView.saveBitmap(mDoodleView)
            }

        })
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
        signature_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}