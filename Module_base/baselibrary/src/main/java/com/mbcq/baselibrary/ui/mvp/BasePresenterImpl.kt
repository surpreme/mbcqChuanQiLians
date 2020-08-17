package com.mbcq.baselibrary.ui.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mbcq.baselibrary.util.log.LogUtils
import org.jetbrains.annotations.NotNull



open class BasePresenterImpl<V : BaseView> : BasePresenter<V>, LifecycleObserver {
    protected var mView: V? = null
    override fun attachView(view: V) {
        mView = view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate(@NotNull owner: LifecycleOwner) {
        LogUtils.e("onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(@NotNull owner: LifecycleOwner) {
        LogUtils.e("onDestroy")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onLifecycleChanged(
            @NotNull owner: LifecycleOwner,
            @NotNull event: Lifecycle.Event
    ) {
        LogUtils.e("onLifecycleChanged")

    }

    override fun detachView() {
        mView = null
    }

    fun showError(s: String) {
        mView?.showError(s)
    }
}