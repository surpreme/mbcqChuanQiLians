package com.mbcq.accountlibrary.activity.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.SystemCacheUtil
import com.mbcq.baselibrary.util.system.SystemUtil
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.dialog.BottomTipDialog
import com.mbcq.commonlibrary.scan.scanlogin.QrCodeDialogFragment
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置页面
 */
@Route(path = ARouterConstants.SettingActivity)
class SettingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_setting

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        app_cache_information_tv.text = SystemCacheUtil.getTotalCacheSize(mContext)
        app_version_information_tv.text = "当前版本号:${SystemUtil.getAppVersionName(mContext)}"

    }

    override fun onClick() {
        super.onClick()
        down_app_ar_code_ll.apply {
            onSingleClicks {
                QrCodeDialogFragment(getScreenWidth()).show(supportFragmentManager, "QrCodeDialogFragment")
            }
        }
        about_us_information_ll.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.AboutUsActivity).navigation()
            }
        }
        app_cache_information_ll.apply {
            onSingleClicks {
                BottomTipDialog("清除缓存", object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        if (s1 == "-1")
                            return
                        SystemCacheUtil.clearAllCache(CommonApplication.getContext())
                        object : CountDownTimer(1000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {

                            }

                            override fun onFinish() {
                                if (!isDestroyed)
                                    app_cache_information_tv.text = SystemCacheUtil.getTotalCacheSize(mContext)

                            }

                        }.start()
                    }

                }).show(supportFragmentManager,"BottomTipDialog")
            }
        }
        setting_activity_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

}