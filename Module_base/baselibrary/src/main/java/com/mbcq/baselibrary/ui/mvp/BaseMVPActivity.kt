package com.mbcq.baselibrary.ui.mvp

import android.Manifest
import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.LoadingDialogFragment
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.tbruyelle.rxpermissions.RxPermissions
import java.lang.reflect.ParameterizedType

/**
 * @Auther: liziyang
 * @datetime: 2020-01-19
 * @desc:
 */
abstract class BaseMVPActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseActivity(), BaseView {
    protected var mIsCanCloseLoading = true
    open fun onDestroys() {
        if (mPresenter != null)
            lifecycle.removeObserver(mPresenter!!)
    }

    open fun isShowErrorDialog(): Boolean {
        return false
    }

    var mPresenter: T? = null

    override fun getContext(): Context {

        return this
    }


    override fun initExtra() {
        super.initExtra()
        mPresenter = getInstance<T>(this, 1)
        mPresenter!!.attachView(this as V)
        lifecycle.addObserver(mPresenter!!)//添加LifecycleObserver

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.detachView()
        onDestroys()
    }

    fun <T> getInstance(o: Any, i: Int): T? {
        try {
            return ((o.javaClass
                    .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                    .newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    private var mLoadingDialogFragment: LoadingDialogFragment? = null
    override fun showLoading() {
        if (mLoadingDialogFragment == null) {
            mLoadingDialogFragment = LoadingDialogFragment()
            mLoadingDialogFragment?.show(supportFragmentManager, "LoadingDialogFragment")
        }

    }

    override fun onStop() {
        super.onStop()
        closeLoading()

    }


    override fun closeLoading() {
        if (mLoadingDialogFragment != null && mIsCanCloseLoading) {
            mLoadingDialogFragment?.dismissAllowingStateLoss()
            mLoadingDialogFragment = null
        }
    }

    override fun showError(msg: String) {
        if (isShowErrorDialog())
            TalkSureDialog(mContext, getScreenWidth(), msg).show()
        else
            ToastUtils.showToast(mContext, msg)
        LogUtils.e(msg + this.localClassName)
    }


}