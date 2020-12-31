package com.mbcq.baselibrary.view

import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity

//<!--解决原生tab无法设置底部指示器宽度的缺陷-->
class SimpleFlycoTabLayout {
    private val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()
    lateinit var mFlycoTabLayout_id: CommonTabLayout
    fun initFlycoTabLayout() {
//        mFlycoTabLayout_id = findViewbyid(0)
        mTabEntities.add(LocalEntity("xxx"))
        mTabEntities.add(LocalEntity("yyy"))
        mFlycoTabLayout_id.setTabData(mTabEntities)
    }
}