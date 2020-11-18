package com.mbcq.baselibrary.util.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration


/**
 * @Auther: valy
 * @datetime: 2020/3/6
 * @desc:
 */
object ScreenSizeUtils {
    /**
     * 系统栏的高度
     */
    fun getStatusBarHeight(mContext: Context): Int = StatusBarUtils.getHeight(mContext)

    @SuppressLint("NewApi")
    fun checkDeviceHasNavigationBar(activity: Context?): Boolean {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        val hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !hasMenuKey && !hasBackKey
    }
    fun getNavigationBarHeight(activity: Context): Int {
        val resources: Resources = activity.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height",
                "dimen", "android")
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId)
    }
    /**
     * 屏幕的尺寸
     */
    fun getScreenWidth(mContext: Context): Int = mContext.resources?.displayMetrics?.widthPixels!!

    fun getScreenHeight(mContext: Context): Int = mContext.resources?.displayMetrics?.heightPixels!!
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }



}