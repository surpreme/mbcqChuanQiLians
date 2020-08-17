package com.mbcq.baselibrary.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlin.collections.ArrayList

/**
 * @Auther: valy
 * @datetime: 2020/3/17
 * @desc:
 */
class FragmentViewPagerAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    val mFragments = fragments
    override fun getItem(position: Int) = mFragments[position]

    override fun getCount(): Int = mFragments.size

}