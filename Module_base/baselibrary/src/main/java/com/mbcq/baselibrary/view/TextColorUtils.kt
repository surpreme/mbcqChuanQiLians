package com.mbcq.baselibrary.view


import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

/**
 * @Auther: lzy
 * @datetime: 2020/3/6
 * @desc:
 */
object TextColorUtils {
    /**
     * 设置textView字体多个颜色
     * "#060606"黑色
     * "#FF5000"橙色
     */
    fun setSpannableStringBuilder(s: String, start: Int, startColor: String, nextColor: String): SpannableStringBuilder {
        if (s == null) {
            throw Exception("StringNull")
        } else {
            val builder = SpannableStringBuilder(s)
            val firstSpan = ForegroundColorSpan(Color.parseColor(startColor))
            val nextSpan = ForegroundColorSpan(Color.parseColor(nextColor))
            builder.setSpan(firstSpan, 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(nextSpan, start, s.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return builder
        }
    }

    fun setSpannableStringBuilder(s: String, start: Int, startColor: Int, nextColor: Int): SpannableStringBuilder {
        if (s == null) {
            throw Exception("StringNull")
        } else {
            val builder = SpannableStringBuilder(s)
            val firstSpan = ForegroundColorSpan(startColor)
            val nextSpan = ForegroundColorSpan(nextColor)
            builder.setSpan(firstSpan, 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(nextSpan, start, s.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return builder
        }
    }
}