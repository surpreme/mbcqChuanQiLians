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
 * 登录页
 */
@Route(path = ARouterConstants.LogInActivity)
class LogInActivity : BaseLogInActivity<LogInContract.View, LogInPresenter>(), LogInContract.View {

    override fun getLayoutId(): Int = R.layout.activity_log_in

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
                if (verificationLogIn()){
                    mPresenter?.logIn(number_get_edit.text.toString(), key_get_edit.text.toString())

                }
            }

        })
    }



    override fun loInS(result: LogInSuccessBean) {
        UserInformationUtil.setUserKey(mContext, result.token)
        UserInformationUtil.setWebIdCode(mContext, result.webidCode)
        if (isSavePsw) {
            UserInformationUtil.setUserAccount(mContext, number_get_edit.text.toString(), key_get_edit.text.toString())
            SPUtil.getInstance().putString(FingerConstant.SP_A_P, key_get_edit.text.toString())
        }
        //本地存储账号用户指纹登录时显示账号信息
        SPUtil.getInstance().putString(FingerConstant.SP_ACCOUNT, number_get_edit.text.toString())
//        SPUtil.getInstance().putString(FingerConstant.SP_A_P, result.token)

        if (!isDestroyed)
            mPresenter?.getWebAreaId()
    }

    override fun getWebAreaIdS(result: String) {
        addWebAreaId(result)
        ARouter.getInstance().build(ARouterConstants.HouseActivity).navigation()
        finish()
    }


    override fun fingerVerificationSuccess() {
        isSavePsw = false
        Log.d("TAG", "fingerVerificationSuccess: ${UserInformationUtil.getUserName(mContext)}--${UserInformationUtil.getUserPsw(mContext)}")
        mPresenter?.logIn(UserInformationUtil.getUserName(mContext), UserInformationUtil.getUserPsw(mContext))

    }


}
