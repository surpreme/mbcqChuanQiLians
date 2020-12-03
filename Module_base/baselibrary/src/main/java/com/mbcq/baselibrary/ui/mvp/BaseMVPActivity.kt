package com.mbcq.baselibrary.ui.mvp

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.LoadingDialogFragment
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.CustomizeToastUtil
import java.lang.reflect.ParameterizedType
import java.util.regex.Pattern

/**
 * @Auther: liziyang
 * @datetime: 2020-01-19
 * @desc: 再次封装 需要第三个泛型请放在后面 否则会闪退
 */
abstract class BaseMVPActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseActivity(), BaseView {
    protected var mIsCanCloseLoading = true


    open fun isShowErrorDialog(): Boolean {
        return false
    }

    var mPresenter: T? = null

    override fun getContext(): Context {

        return this
    }

    override fun UnToken(msg: String) {
        TalkSureDialog(mContext, getScreenWidth(), "登录身份失效，您需要重新登录") {
            ARouter.getInstance().build("/account/LogInActivity").navigation()
            this.finish()
        }.show()

    }

    override fun initExtra() {
        super.initExtra()
        mPresenter = getInstance<T>(this, 1)
        mPresenter?.let {
            it.attachView(this as V)
            lifecycle.addObserver(it)//添加LifecycleObserver
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let {
            lifecycle.removeObserver(it)
            it.detachView()

        }
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
        if (mLoadingDialogFragment == null && mIsCanCloseLoading) {
            mLoadingDialogFragment = LoadingDialogFragment()
            mLoadingDialogFragment?.showAllowingStateLoss(supportFragmentManager, "BaseLoadingDialogFragment")
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
        else {
            ToastUtils.showToast(mContext, msg)
            //*************************
            /*  val toastUtil = CustomizeToastUtil()
              toastUtil.Short(mContext, msg).setGravity(Gravity.TOP).setErrorToast(Color.WHITE, R.drawable.toast_radius).show()*/
        }
        LogUtils.e(msg + this.localClassName)
    }


    /**
     * * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    protected fun isInteger(str: String): Boolean {
        val pattern: Pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(str).matches()
    }

}