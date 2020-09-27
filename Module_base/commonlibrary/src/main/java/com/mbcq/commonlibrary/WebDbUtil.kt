package com.mbcq.commonlibrary

import android.app.Application
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao

object WebDbUtil {



    /**
     * 得到greenDao数据库中的网点
     * 可视化 stetho 度娘
     */
    fun getDbWebId(application: Application, mWebDbInterface: WebsDbInterface) {
        val daoSession: DaoSession = (application as CommonApplication).daoSession
        val userInfoDao: WebAreaDbInfoDao = daoSession.webAreaDbInfoDao
        val dbDatas = userInfoDao.queryBuilder().list()
        if (dbDatas.isNullOrEmpty()) {
            mWebDbInterface.isNull()
        } else {
            mWebDbInterface.isSuccess(dbDatas)
        }
    }
}