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
import com.mbcq.commonlibrary.DbConstant
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoMaster
import com.mbcq.commonlibrary.greendao.DaoMaster.DevOpenHelper
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.commonlibrary.scan.scanlogin.QrCodeDialogFragment
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_log_in.*


/**
 * 处理数据库
 *
 */
abstract class BaseDbLogInActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseFingerLogInMVPActivity<V, T>(), BaseView {

    override fun initExtra() {
        super.initExtra()
        val daoSession: DaoSession = (application as CommonApplication).daoSession
        val userInfoDao: WebAreaDbInfoDao = daoSession.webAreaDbInfoDao
        userInfoDao.deleteAll()
    }


    protected fun addWebAreaId(data: String) {
        val devOpenHelper = DevOpenHelper(applicationContext, DbConstant.WEB_AREA_DB, null)
        val daoMaster = DaoMaster(devOpenHelper.writableDb)
        val daoSession = daoMaster.newSession()
        val webAreaListBean = Gson().fromJson<List<WebAreaDbInfo>>(data, object : TypeToken<List<WebAreaDbInfo>>() {}.type)
        for (mIndex in webAreaListBean.indices) {
            webAreaListBean[mIndex]._id = mIndex.toLong()
            daoSession.insertOrReplace(webAreaListBean[mIndex])
        }
    }


}
