package com.mbcq.accountlibrary.activity.house

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.security.keystore.KeyProperties
import android.util.Log
import android.view.View
import com.mbcq.baselibrary.db.SPUtil
import com.mbcq.baselibrary.dialog.common.CommonTipDialog
import com.mbcq.baselibrary.finger.FingerConstant
import com.mbcq.baselibrary.finger.FingerprintHelper
import com.mbcq.baselibrary.finger.FingerprintUtil
import com.mbcq.baselibrary.finger.FingerprintVerifyDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil

/**
 *开启指纹
 */
abstract class BaseHouseFingerActivity : BaseActivity(), FingerprintHelper.SimpleAuthenticationCallback {
    lateinit var helper: FingerprintHelper
    private var openFingerprintLoginTipDialog: CommonTipDialog? = null
    private var fingerprintVerifyDialog: FingerprintVerifyDialog? = null

    override fun initExtra() {
        super.initExtra()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        helper = FingerprintHelper.getInstance()
        helper.init(applicationContext)
        helper.setCallback(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initFingerTips()
        }

    }

    private fun showFingerprintVerifyErrorInfo(info: String) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.setContentText(info)
        }
    }

    private fun initFingerTips() {
        object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                if (helper.checkFingerprintAvailable(mContext) != -1) {
                    if (UserInformationUtil.getUserIsAskFingerLogIn(mContext)) {
                        showOpenFingerprintLoginDialog()
                    }
                }
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()

    }

    private fun showOpenFingerprintLoginDialog() {
        if (openFingerprintLoginTipDialog == null) {
            openFingerprintLoginTipDialog = CommonTipDialog(mContext)
        }
        openFingerprintLoginTipDialog?.setSingleButton(false)
        openFingerprintLoginTipDialog?.setContentText("您的设备支持指纹登录,是否现在开启?")
        openFingerprintLoginTipDialog?.setOnDialogButtonsClickListener(object : CommonTipDialog.OnDialogButtonsClickListener {
            override fun onNoAgainAskClick(v: View?) {
                UserInformationUtil.setUserIsFingerLogIn(mContext, false)
                UserInformationUtil.setUserIsAskFingerLogIn(mContext, false)

            }

            override fun onCancelClick(v: View?) {
                UserInformationUtil.setUserIsAskFingerLogIn(mContext, true)

            }

            override fun onConfirmClick(v: View?) {
                openFingerprintLogin()

            }

        })
        openFingerprintLoginTipDialog?.show()
    }

    private var fingerprintVerifyErrorTipDialog: CommonTipDialog? = null

    private fun showTipDialog(errorCode: Int, errString: CharSequence) {
        if (fingerprintVerifyErrorTipDialog == null) {
            fingerprintVerifyErrorTipDialog = CommonTipDialog(mContext)
        }
        fingerprintVerifyErrorTipDialog?.setContentText("$errString")
        fingerprintVerifyErrorTipDialog?.setSingleButton(true)
        fingerprintVerifyErrorTipDialog?.setOnSingleConfirmButtonClickListener {
            helper.stopAuthenticate()
        }
        fingerprintVerifyErrorTipDialog?.show()
    }

    private fun openFingerprintLogin() {
        helper.generateKey()
        if (fingerprintVerifyDialog == null) {
            fingerprintVerifyDialog = FingerprintVerifyDialog(mContext)
        }
        fingerprintVerifyDialog?.setContentText("请验证指纹")
        fingerprintVerifyDialog?.setOnCancelButtonClickListener {
            helper.stopAuthenticate()
        }
        fingerprintVerifyDialog?.show()
        helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT)
        helper.authenticate()
    }

    override fun onAuthenticationSucceeded(value: String?) {
        Log.d("hagan", "HomeActivity->onAuthenticationSucceeded-> value:$value")
        SPUtil.getInstance().putBoolean(FingerConstant.SP_HAD_OPEN_FINGERPRINT_LOGIN, true)

        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.dismiss()
            showToast("指纹登录已开启")
            UserInformationUtil.setUserIsFingerLogIn(mContext, true)
            UserInformationUtil.setUserIsAskFingerLogIn(mContext, false)
            saveLocalFingerprintInfo()

        }

    }

    fun saveLocalFingerprintInfo() {
        SPUtil.getInstance().putString(FingerConstant.SP_LOCAL_FINGERPRINT_INFO, FingerprintUtil.getFingerprintInfoString(applicationContext))
    }

    override fun onAuthenticationFail() {
        showFingerprintVerifyErrorInfo("指纹不匹配")

    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Log.e("hagan", "HomeActivity->onAuthenticationError-> errorCode:$errorCode,errString:$errString")
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.dismiss()
        }
        showTipDialog(errorCode, errString.toString())
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        Log.e("hagan", "HomeActivity->onAuthenticationHelp-> helpCode:$helpCode,helpString:$helpString")
        showFingerprintVerifyErrorInfo(helpString.toString())

    }

}
