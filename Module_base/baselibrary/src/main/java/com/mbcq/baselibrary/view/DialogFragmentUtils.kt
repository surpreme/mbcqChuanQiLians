package com.mbcq.baselibrary.view

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

object DialogFragmentUtils {
    /**
     * 主要是用来判断dialogfragment是否存在
     */
    fun getIsShowDialogFragment(fa: FragmentActivity): Boolean {
        val mShowFragment = fa.supportFragmentManager.fragments
        if (!mShowFragment.isNullOrEmpty()) {
            for (itemFragment in mShowFragment) {
                if (itemFragment is DialogFragment) {
                    return true
                }
            }
        }
        return false
    }

}