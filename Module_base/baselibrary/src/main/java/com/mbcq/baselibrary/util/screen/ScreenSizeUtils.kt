package com.mbcq.baselibrary.util.screen

import android.content.Context

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