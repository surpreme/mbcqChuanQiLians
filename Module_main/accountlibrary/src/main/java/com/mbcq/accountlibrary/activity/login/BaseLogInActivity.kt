package com.mbcq.accountlibrary.activity.login

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.db.SPUtil
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.finger.FingerConstant
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoMaster
import com.mbcq.commonlibrary.greendao.DaoMaster.DevOpenHelper
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.commonlibrary.scan.scanlogin.QrCodeDialogFragment
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_log_in.*


/**
 * 登录页 杂乱的放这里
 */
abstract class BaseLogInActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseDbLogInActivity<V, T>(), BaseView {
    lateinit var rxPermissions: RxPermissions
    var isSavePsw: Boolean = true
    override fun isShowErrorDialog(): Boolean = true

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_gray)
        rxPermissions = RxPermissions(this)
        number_get_edit.setText(UserInformationUtil.getUserName(mContext))
        getCallPhone()
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

    private fun getCallPhone() {
        //
        rxPermissions.request(Manifest.permission.CALL_PHONE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
//                        ScanDialogFragment(getScreenWidth()).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                    }
                }
    }





}
