package com.mbcq.baselibrary.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.baselibrary.view.SingleClick
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

/**Lqayou
 * @Auther: liziyang
 * @datetime: 2020-01-18
 * @desc:
 * ?.意思是这个参数可以为空,并且程序继续运行下去
 * !!.的意思是这个参数如果为空,就抛出异常
 */
abstract class BaseFragment : Fragment() {
    abstract fun getLayoutResId(): Int
    abstract fun initViews(view: View)
    open fun initViews(view: View, savedInstanceState: Bundle) {}
    open fun initDatas() {}
    protected lateinit var mContext: Context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initExtra()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initDatas()
    }

    /**
     * 系统栏的高度
     */
    protected fun getStatusBarHeight(): Int {
        return StatusBarUtils.getHeight(mContext!!)
    }


    open fun hideKeyboard(view: View) {
        val imm = mContext?.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * toast
     */
    protected fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()

    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    open fun showShort(@StringRes resId: Int) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show()
    }

    fun startActivity(mClz: Class<*>) {
        startActivity(Intent(activity, mClz))
    }


    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(activity!!, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
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
    protected fun getScreenHeight(): Int {
        return ScreenSizeUtils.getScreenHeight(mContext!!)
    }

    /**
     * 转换字符为小数后两位
     * 格式化，区小数后两位
     */
    protected open fun haveTwoDouble(d: Double): String? {
        val df = DecimalFormat("0.00")
        return try {
            df.format(d)
        } catch (e: Exception) {
            LogUtils.e(d)
            ""
        }
    }

}

