package com.mbcq.baselibrary.view

import com.google.android.material.tabs.TabLayout

/**
 * @Auther: lzy
 * @datetime: 2020/3/20
 * @desc:
 */
abstract class TabBuilder : TabLayout.OnTabSelectedListener {
    abstract fun onSelected(tab: TabLayout.Tab)
    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        onSelected(tab)
    }

}