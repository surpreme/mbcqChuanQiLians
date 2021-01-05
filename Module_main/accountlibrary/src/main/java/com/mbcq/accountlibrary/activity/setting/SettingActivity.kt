package com.mbcq.accountlibrary.activity.setting

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.activity.facerecognition.RegisterAndRecognizeActivity
import com.mbcq.accountlibrary.activity.house.BaseHouseFingerActivity
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.SystemCacheUtil
import com.mbcq.baselibrary.util.system.SystemUtil
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.PasswordDialog
import com.mbcq.commonlibrary.dialog.BottomTipDialog
import com.mbcq.commonlibrary.scan.scanlogin.QrCodeDialogFragment
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置页面
 */
@Route(path = ARouterConstants.SettingActivity)
class SettingActivity : BaseHouseFingerActivity() {
    lateinit var rxPermissions: RxPermissions
    var isFaceOpenLogIn = false

    override fun getLayoutId(): Int = R.layout.activity_setting
    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
    }


    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        app_cache_information_tv.text = SystemCacheUtil.getTotalCacheSize(mContext)
        app_version_information_tv.text = "当前版本号:${SystemUtil.getAppVersionName(mContext)}"
        val hongruanFaceShareP = SharePreferencesHelper(mContext, Constant.HONGRUAN_SHAREPREFERENCES)
        isFaceOpenLogIn = hongruanFaceShareP.getSharePreference(Constant.HONGRUAN_IS_OPEN_FACE, false) as Boolean
        face_recognition_state_tv.text = if (isFaceOpenLogIn) "已打开" else "未添加"
        finger_recognition_state_tv.text = if (UserInformationUtil.getUserIsFingerLogIn(mContext)) "已启用" else "点击打开指纹登录"
    }

    override fun onClick() {
        super.onClick()
        finger_recognition_ll.apply {
            onSingleClicks {
                if (finger_recognition_state_tv.text.toString() == "点击打开指纹登录") {
                    PasswordDialog(UserInformationUtil.getUserPsw(mContext), object : OnClickInterface.OnClickInterface {
                        override fun onResult(s1: String, s2: String) {
                            UserInformationUtil.setUserIsFingerLogIn(mContext, false)
                            UserInformationUtil.setUserIsAskFingerLogIn(mContext, true)
                            initFingerTips()
                        }
                    }).show(supportFragmentManager, "PasswordDialogX")

                } else {
                    showToast("您的指纹已经注册，不需要重新注册")
                }
            }
        }
        face_recognition_ll.apply {
            onSingleClicks {
                if (!isFaceOpenLogIn) {
                    getCamera()
                } else
                    showToast("您已经注册过脸部不需要再次注册")
            }
        }
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

                }).show(supportFragmentManager, "BottomTipDialog")
            }
        }
        setting_activity_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

    protected fun getCamera() {
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        PasswordDialog(UserInformationUtil.getUserPsw(mContext), object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                if (s1 == "1") {
                                    val bundle = Bundle()
                                    bundle.putInt("RegisterAndRecognizeType", 2)
                                    startActivity(RegisterAndRecognizeActivity::class.java, bundle)
                                }
                            }

                        }).show(supportFragmentManager, "PasswordDialog")

                    } else {
                    }
                }
    }


}