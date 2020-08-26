package com.mbcq.accountlibrary.activity.login

import android.content.Intent
import android.security.keystore.KeyProperties
import android.util.Log
import com.mbcq.baselibrary.db.SPUtil
import com.mbcq.baselibrary.dialog.common.CommonTipDialog
import com.mbcq.baselibrary.finger.FingerConstant
import com.mbcq.baselibrary.finger.FingerprintHelper
import com.mbcq.baselibrary.finger.FingerprintUtil
import com.mbcq.baselibrary.finger.FingerprintVerifyDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.UserInformationUtil

/**
 * 指纹登陆
 */
abstract class BaseFingerLogInMVPActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V,T>(), BaseView, FingerprintHelper.SimpleAuthenticationCallback {
    lateinit var helper: FingerprintHelper
    private var fingerprintVerifyDialog: FingerprintVerifyDialog? = null
    private var fingerprintChangeTipDialog: CommonTipDialog? = null
    private var errorTipDialog: CommonTipDialog? = null

    override fun initExtra() {
        super.initExtra()
        helper = FingerprintHelper.getInstance()
        helper.init(getContext())
        helper.setCallback(this)
    }

    open fun openFingerprintLogin() {
        Log.e("hagan", "FingerprintLoginFragment->openFingerprintLogin")

        //验证指纹库信息是否发生变化
        if (FingerprintUtil.isLocalFingerprintInfoChange(getContext())) {
            //指纹库信息发生变化
            showFingerprintChangeTipDialog()
            return
        }
        if (fingerprintVerifyDialog == null) {
            fingerprintVerifyDialog = FingerprintVerifyDialog(mContext)
        }
        fingerprintVerifyDialog?.setContentText("请验证指纹")
        fingerprintVerifyDialog?.setOnCancelButtonClickListener {
            helper.stopAuthenticate()
        }
        fingerprintVerifyDialog?.show()
        helper.setPurpose(KeyProperties.PURPOSE_DECRYPT)
        helper.authenticate()
    }

     fun showFingerprintChangeTipDialog() {
        if (fingerprintChangeTipDialog == null) {
            fingerprintChangeTipDialog = CommonTipDialog(mContext)
        }
        fingerprintChangeTipDialog?.setContentText("您设备的指纹发生了变化或系统出错,指纹登录已关闭.请使用其他方式登录后重新开启.")
        fingerprintChangeTipDialog?.setSingleButton(true)
        fingerprintChangeTipDialog?.setOnSingleConfirmButtonClickListener {
            // iv_fingerprint_login.setEnabled(false)
            SPUtil.getInstance().putBoolean(FingerConstant.SP_HAD_OPEN_FINGERPRINT_LOGIN, false)
            UserInformationUtil.setUserIsFingerLogIn(mContext, false)
            UserInformationUtil.setUserIsAskFingerLogIn(mContext, true)
            helper.closeAuthenticate()
        }
        fingerprintChangeTipDialog?.show()
    }

    fun showFingerprintVerifyErrorInfo(info: String) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.setContentText(info)
        }
    }

    fun showTipDialog(errorCode: Int, errString: CharSequence) {
        if (errorTipDialog == null) {
            errorTipDialog = CommonTipDialog(mContext)
        }
        errorTipDialog?.setContentText("$errString")
        errorTipDialog?.setSingleButton(true)
        errorTipDialog?.setOnSingleConfirmButtonClickListener {
            helper.stopAuthenticate()
        }
        errorTipDialog?.show()
    }

    abstract fun fingerVerificationSuccess()
    override fun onAuthenticationSucceeded(value: String?) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationSucceeded-> value:$value")
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.dismiss()
        }
        fingerVerificationSuccess()
    }

    override fun onAuthenticationFail() {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationFail")
        showFingerprintVerifyErrorInfo("指纹不匹配")
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationSucceeded-> errString:" + errString.toString())
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.dismiss()
        }
        showTipDialog(errorCode, errString.toString())
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationHelp-> helpString:$helpString")
        showFingerprintVerifyErrorInfo(helpString.toString())
    }
}