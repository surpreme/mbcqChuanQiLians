package com.mbcq.baselibrary.ui.mvp

import android.content.Context
import com.mbcq.baselibrary.dialog.dialogfragment.LoadingDialogFragment
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.util.log.LogUtils
import java.lang.reflect.ParameterizedType

/**
 * @Auther: lzy
 * @datetime: 2020/3/13
 * @desc:
 */
abstract class BaseMVPListFragment<V : BaseView, T : BasePresenterImpl<V>> : BaseFragment(), BaseView {
    open fun onDestroys(){}
    var mPresenter: T? = null


    override fun initExtra() {
        super.initExtra()
        mPresenter = getInstance<T>(this, 1)
        mPresenter!!.attachView(this as V)

    }


    override fun getContext(): Context = activity!!

    override fun showError(msg: String) {
        showToast(msg)
        LogUtils.e(msg)
    }

    var mLoadingDialogFragment: LoadingDialogFragment? = null
    override fun showLoading() {
        mLoadingDialogFragment = LoadingDialogFragment()
        mLoadingDialogFragment?.show(childFragmentManager, "LoadingDialogFragment")
    }

    override fun onStop() {
        super.onStop()
        closeLoading()
    }

    override fun closeLoading() {
        if (mLoadingDialogFragment != null) {
            if (mLoadingDialogFragment!!.isResumed)
                mLoadingDialogFragment?.dismiss()
        }


    }

    fun <T> getInstance(o: Any, i: Int): T? {
        try {
            return ((o.javaClass
                    .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                    .newInstance()
        } catch (e: java.lang.InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }
}