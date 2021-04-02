package com.mbcq.accountlibrary.activity.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import com.alibaba.android.arouter.launcher.ARouter
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.activity.facerecognition.RegisterAndRecognizeActivity
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_log_in.*


/**
 * 登录页 杂乱的放这里
 */
abstract class BaseLogInActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseDbLogInActivity<V, T>(), BaseView {
    lateinit var rxPermissions: RxPermissions
    var isSavePsw: Boolean = true
    override fun isShowErrorDialog(): Boolean = true
    var isFaceActive = false
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_gray)
        rxPermissions = RxPermissions(this)
        number_get_edit.setText(UserInformationUtil.getUserName(mContext))
        getFaceEngine()

    }

    private fun getFaceEngine() {
        val hongruanFaceShareP = SharePreferencesHelper(mContext, Constant.HONGRUAN_SHAREPREFERENCES)
        isFaceActive = hongruanFaceShareP.getSharePreference(Constant.HONGRUAN_IS_ACTIVATE, false) as Boolean
        if (!isFaceActive) {
            val activeCode = FaceEngine.activeOnline(mContext, Constant.HONGRUAN_APP_ID, Constant.HONGRUAN_SDK_KEY)
            if (activeCode == ErrorInfo.MOK) {
                LogUtils.d("人脸引擎激活成功")
                hongruanFaceShareP.put(Constant.HONGRUAN_IS_ACTIVATE, true)
            } else {
                TalkSureDialog(mContext, getScreenWidth(), "人脸引擎激活失败 $activeCode").show()
            }

        }
    }


    protected fun verificationLogIn(): Boolean {
        if (number_get_edit.text.toString().isEmpty()) {
            showToast("请输入用户名")
            return false
        }
        if (key_get_edit.text.toString().isEmpty()) {
            showToast("请输入密码")
            return false
        }
        return true
    }

    protected fun sendCode() {
        verification_code_tv.isClickable = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verification_code_tv.setBackgroundColor(getColor(R.color.base_gray))
        } else {
            verification_code_tv.setBackgroundColor(resources.getColor(R.color.base_gray))

        }
        showToast("已发送验证码")
        object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                verification_code_tv.text = "获取验证码"
                verification_code_tv.isClickable = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    verification_code_tv.setBackgroundColor(getColor(R.color.white))
                } else {
                    verification_code_tv.setBackgroundColor(resources.getColor(R.color.white))

                }
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                verification_code_tv.text = "${(millisUntilFinished / 1000)} S"

            }

        }.start()
    }

    protected fun getCamera() {
        //
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
//                        ScanDialogFragment(getScreenWidth()).show(supportFragmentManager, "ScanDialogFragment")
                        if (isFaceActive) {
                            val intent=Intent()
                            intent.setClass(mContext,RegisterAndRecognizeActivity::class.java)
                            intent.putExtra("RegisterAndRecognizeType", 1)
                            val bundle = Bundle()
                            bundle.putInt("RegisterAndRecognizeType", 1)
                            startActivityForResult(intent,7840,bundle)
                            /* ARouter.getInstance().build(ARouterConstants.FaceRecognitionActivity).navigation()*/
                        }else{
                            showToast("人脸识别初始化失败，请稍后再试")
                        }
                    } else {
                        // Oups permission denied
                    }
                }
    }


}
