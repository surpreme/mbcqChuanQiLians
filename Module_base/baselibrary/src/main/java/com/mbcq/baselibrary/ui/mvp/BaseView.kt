package com.mbcq.baselibrary.ui.mvp

import android.content.Context


interface BaseView {
    fun getContext(): Context
    fun showError(msg: String)
    fun closeLoading()
    fun showLoading()
    fun UnToken(msg: String)

}