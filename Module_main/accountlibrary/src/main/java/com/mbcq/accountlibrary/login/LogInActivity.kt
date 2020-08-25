package com.mbcq.accountlibrary.login

import android.Manifest
import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.db.SPUtil
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.finger.FingerConstant
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.UserInformationUtil
import com.mbcq.commonlibrary.scan.scanlogin.QrCodeDialogFragment
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_log_in.*

/**
 * 登录页
 */
@Route(path = ARouterConstants.LogInActivity)
class LogInActivity : BaseFingerLogInMVPActivity<LogInContract.View, LogInPresenter>(), LogInContract.View {
    lateinit var rxPermissions: RxPermissions
    var isSavePsw: Boolean = true
    override fun getLayoutId(): Int = R.layout.activity_log_in
    override fun isShowErrorDialog(): Boolean = true

    override fun initViews() {
        super.initViews()
        setStatusBar(R.color.base_gray)
        rxPermissions = RxPermissions(this)
        number_get_edit.setText(UserInformationUtil.getUserName(mContext))
        getCallPhone()
    }

    override fun onClick() {
        super.onClick()
        verification_code_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                sendCode()

            }

        })
        qrcode_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                QrCodeDialogFragment(getScreenWidth()).show(supportFragmentManager, "QrCodeDialogFragment")
            }

        })
        finger_login_ll.setOnClickListener {
            if (UserInformationUtil.getUserIsFingerLogIn(mContext)) {
                openFingerprintLogin()
            } else {
                TalkSureDialog(mContext, getScreenWidth(), "登录后打开指纹登录即可使用").show()
            }
        }
        login_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                verificationLogIn()
            }

        })
    }

    private fun verificationLogIn() {
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

    private fun sendCode() {
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


    override fun loInS(result: LogInSuccessBean) {
        UserInformationUtil.setUserKey(mContext, result.token)
        if (isSavePsw) {
            UserInformationUtil.setUserAccount(mContext, number_get_edit.text.toString(), key_get_edit.text.toString())
            SPUtil.getInstance().putString(FingerConstant.SP_A_P, key_get_edit.text.toString())
        }
        //本地存储账号用户指纹登录时显示账号信息
        //本地存储账号用户指纹登录时显示账号信息
        SPUtil.getInstance().putString(FingerConstant.SP_ACCOUNT, number_get_edit.text.toString())
//        SPUtil.getInstance().putString(FingerConstant.SP_A_P, result.token)

        object : CountDownTimer(500, 500) {
            override fun onFinish() {
                if (!isDestroyed) {
                    ARouter.getInstance().build(ARouterConstants.HouseActivity).navigation()
                }
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()

    }


    override fun fingerVerificationSuccess() {
        isSavePsw = false
        Log.d("TAG", "fingerVerificationSuccess: ${UserInformationUtil.getUserName(mContext)}--${UserInformationUtil.getUserPsw(mContext)}")
        mPresenter?.logIn(UserInformationUtil.getUserName(mContext), UserInformationUtil.getUserPsw(mContext))

    }


}
