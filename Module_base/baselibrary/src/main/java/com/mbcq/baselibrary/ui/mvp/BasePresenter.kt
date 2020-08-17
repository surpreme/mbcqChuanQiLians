package com.mbcq.baselibrary.ui.mvp


interface BasePresenter<V : BaseView> {
    fun attachView(view: V)

    fun detachView()
}
