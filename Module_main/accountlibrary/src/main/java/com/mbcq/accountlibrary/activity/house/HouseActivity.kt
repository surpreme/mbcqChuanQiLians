package com.mbcq.accountlibrary.activity.house


import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao


/**
 * 主页面
 */
@Route(path = ARouterConstants.HouseActivity)
class HouseActivity : BaseFragmentHouseActivity()
