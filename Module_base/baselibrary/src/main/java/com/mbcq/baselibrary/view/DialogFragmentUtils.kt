package com.mbcq.baselibrary.view

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

object DialogFragmentUtils {
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