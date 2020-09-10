package com.mbcq.baselibrary.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import java.text.DecimalFormat


abstract class BaseActivity : AppCompatActivity() {
    abstract fun getLayoutId(): Int
    open fun initViews(savedInstanceState: Bundle?) {}
    open fun initDatas() {}
    protected lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mContext = this

        initExtra()
        initViews(savedInstanceState)
        initDatas()
    }

    override fun onStart() {
        super.onStart()
        onClick()
    }

    /**
     * 为二次封装留下的初始化
     */
    open fun initExtra() {
    }

    /**
     * onclick
     */
    open fun onClick() {}

    /**
     * ->0默认为白色
     * ->1透明
     * ->string html5颜色
     * if (s !is Int)
     */
    protected fun setStatusBar(s: Any) {
        when (s) {

            0 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StatusBarUtils.setColor(this, getColor(R.color.white))
                }
            }
            1 -> {
                StatusBarUtils.setTransparent(mContext)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (s is Int)
                        StatusBarUtils.setColor(this, getColor(s))
                }
            }
        }
    }


    /**
     * 系统栏的高度
     */
    protected fun getStatusBarHeight(): Int {
        return StatusBarUtils.getHeight(mContext)
    }


    open fun hideKeyboard(view: View) {
        val imm = getSystemService(
                Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * toast
     */
    protected fun showToast(msg: String) {
        ToastUtils.showToast(mContext,msg)

    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    open fun showShort(@StringRes resId: Int) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转activity
     */
    fun startActivity(mClz: Class<*>) {
        startActivity(Intent(this, mClz))
    }

    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(this, clz)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * 屏幕的尺寸
     */
    protected fun getScreenWidth(): Int {
        return ScreenSizeUtils.getScreenWidth(mContext)
    }

    protected fun getScreenHeight(): Int {
        return ScreenSizeUtils.getScreenHeight(mContext)
    }

    /**
     * 转换字符为小数后两位
     * 格式化，区小数后两位
     */
    protected open fun haveTwoDouble(d: Double): String {
        val df = DecimalFormat("0.00")
        return try {
            df.format(d)
        } catch (e: Exception) {
            LogUtils.e(d)
            ""
        }
    }
}