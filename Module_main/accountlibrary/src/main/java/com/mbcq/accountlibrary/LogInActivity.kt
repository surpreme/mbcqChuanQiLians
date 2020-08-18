package com.mbcq.accountlibrary

import android.Manifest
import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.login.LogInContract
import com.mbcq.accountlibrary.login.LogInPresenter
import com.mbcq.accountlibrary.login.LogInSuccessBean
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_log_in.*

/**
 * 登录页
 */
@Route(path = ARouterConstants.LogInActivity)
class LogInActivity : BaseMVPActivity<LogInContract.View, LogInPresenter>(), LogInContract.View {
    lateinit var rxPermissions: RxPermissions
    override fun getLayoutId(): Int = R.layout.activity_log_in
    override fun initViews() {
        super.initViews()
        setStatusBar(R.color.base_gray)
        rxPermissions =  RxPermissions(this)


    }

    override fun onClick() {
        super.onClick()
        verification_code_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                verification_code_tv.isClickable = false
                verification_code_tv.setBackgroundColor(getColor(R.color.base_gray))
                showToast("已发送验证码")
                object : CountDownTimer(60 * 1000, 1000) {
                    override fun onFinish() {
                        verification_code_tv.text = "获取验证码"
                        verification_code_tv.isClickable = true
                        verification_code_tv.setBackgroundColor(getColor(R.color.white))

                    }

                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        verification_code_tv.text = "${(millisUntilFinished / 1000)} S"

                    }

                }.start()
            }

        })
        scan_login_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCamera()
            }

        })
        login_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (number_get_edit.text.toString().isEmpty()) {
                    showToast("请输入用户名")
                    return
                }
                if (key_get_edit.text.toString().isEmpty()) {
                    showToast("请输入密码")
                    return
                }
                mPresenter?.logIn(number_get_edit.text.toString(), key_get_edit.text.toString())
            }

        })
    }
    private fun getCamera(){
        //
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth()).show(supportFragmentManager,"ScanDialogFragment")
                    } else {
                        // Oups permission denied
                    }
                }
    }
    override fun isShowErrorDialog(): Boolean = true

    override fun loInS(result: LogInSuccessBean) {
        TalkSureDialog(mContext, getScreenWidth(), "登录成功").show()

    }


}
