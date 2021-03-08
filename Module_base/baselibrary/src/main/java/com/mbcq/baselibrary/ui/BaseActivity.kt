package com.mbcq.baselibrary.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.CustomizeToastUtil
import com.mbcq.baselibrary.view.SingleClick
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern


abstract class BaseActivity : AppCompatActivity() {
    abstract fun getLayoutId(): Int
    open fun initViews(savedInstanceState: Bundle?) {}
    open fun initDatas() {}
    open fun onBeforeCreate() {}
    protected lateinit var mContext: Context
    /**
     * eventBus
     * true必须实现接收方法 @Subscribe
     * 粘性事件只会接收到最后一条数据 @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
     * @Auther liziyang @datetime 21-02-25
     */
    open fun isOpenEventBus(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBeforeCreate()
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
        if (isOpenEventBus())
            EventBus.getDefault().register(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (isOpenEventBus())
            EventBus.getDefault().unregister(this)
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
    protected fun setStatusBar(s: Int) {
        when (s) {

            0 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StatusBarUtils.setColor(this, getColor(R.color.white))
                } else {
                    StatusBarUtils.setColor(this, ContextCompat.getColor(mContext, R.color.base_gray))
                }
            }
            1 -> {
                StatusBarUtils.setTransparent(mContext)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StatusBarUtils.setColor(this, getColor(s))
                } else {
                    StatusBarUtils.setColor(this, ContextCompat.getColor(mContext, s))

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
        ToastUtils.showToast(mContext, msg)
//        val toastUtil = CustomizeToastUtil()
//        toastUtil.Short(mContext, msg).setGravity(Gravity.TOP).setErrorToast(Color.WHITE, R.drawable.toast_radius).show()
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

    protected open fun checkStrIsNum(str: String): Boolean {
        try {
            /** 先将str转成BigDecimal，然后在转成String  */
            BigDecimal(str).toString()
        } catch (e: java.lang.Exception) {
            /** 如果转换数字失败，说明该str并非全部为数字  */
            return false
        }
        val isNum: Matcher = Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(str)
        return isNum.matches()
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

    protected fun getBeanString(s: String?): String {
        return when {
            s == null -> {
                ""
            }
            s.toLowerCase() == "null" -> {
                ""
            }
            else -> {
                s
            }
        }

    }
}

fun View.onSingleClicks(onSingleClick: (View) -> Unit) {
    this.setOnClickListener(object : SingleClick() {
        override fun onSingleClick(v: View) {
            onSingleClick.invoke(v)
        }

    })
}
