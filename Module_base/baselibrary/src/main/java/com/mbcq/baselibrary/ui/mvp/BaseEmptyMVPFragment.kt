package com.mbcq.baselibrary.ui.mvp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.LoadingDialogFragment
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import java.lang.reflect.ParameterizedType

/**
 * @Auther: lzy
 * @datetime: 2020/3/13
 * @desc: 由于高德地图无法适应封装的生命周期 这里所有的封装和生命周期无关
 *        空白的mvp
 */
abstract class BaseEmptyMVPFragment<V : BaseView, T : BasePresenterImpl<V>> : Fragment(), BaseView {
    var mPresenter: T? = null
    protected var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getInstance<T>(this, 1)
        mPresenter?.attachView(this as V)
    }

    override fun UnToken(msg: String) {
        activity?.let {
            TalkSureDialog(it, getScreenWidth(), "登录身份失效，您需要重新登录") {
                ARouter.getInstance().build("/account/LogInActivity").navigation()
            }.show()
        }


    }

    /**
     * 屏幕的尺寸
     */
    protected fun getScreenWidth(): Int {
        mContext?.let {
            return ScreenSizeUtils.getScreenWidth(it)

        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()

    }

    override fun getContext(): Context = activity!!

    override fun showError(msg: String) {
        ToastUtils.showToast(mContext, msg)
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