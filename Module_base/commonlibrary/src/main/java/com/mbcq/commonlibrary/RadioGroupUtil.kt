package com.mbcq.commonlibrary

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.content.res.AppCompatResources
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils

/**
 *    private fun addSelectItem(value: String, index: Int) {
val radioButton = RadioButton(mContext)
radioButton.height = ScreenSizeUtils.dp2px(mContext, 90f)
radioButton.gravity = Gravity.CENTER
radioButton.id = index
radioButton.text = value
radioButton.setBackgroundResource(R.drawable.blue_gray_select)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
radioButton.setTextColor(getColorStateList(R.color.blue_gray_select_color))
} else {
radioButton.setTextColor(AppCompatResources.getColorStateList(mContext, R.color.blue_gray_select_color))
}
val layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT)
//        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
layoutParams.setMargins(ScreenSizeUtils.dp2px(mContext, 12f), 0, 0, 0) //4个参数按顺序分别是左上右下
radioButton.layoutParams = layoutParams
radioButton.setPadding(ScreenSizeUtils.dp2px(mContext, 10f), 0, ScreenSizeUtils.dp2px(mContext, 10f), 0)
radioButton.setButtonDrawable(android.R.color.transparent);

transport_method_rg.addView(radioButton)
}

private fun setTextViewStyle(value: String, isBlue: Boolean, type: Int) {
val textView = TextView(mContext)
textView.height = ScreenSizeUtils.dp2px(mContext, 30f)
textView.gravity = Gravity.CENTER
textView.setPadding(ScreenSizeUtils.dp2px(mContext, 10f), 0, ScreenSizeUtils.dp2px(mContext, 10f), 0)
textView.text = value
if (isBlue) {
textView.background = ContextCompat.getDrawable(mContext, R.drawable.round_blue)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
textView.setTextColor(getColor(R.color.white))
} else {
textView.setTextColor(resources.getColor(R.color.white))

}
} else {
textView.background = ContextCompat.getDrawable(mContext, R.drawable.hollow_out_gray)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
textView.setTextColor(getColor(R.color.black))
} else {
textView.setTextColor(resources.getColor(R.color.black))
}
}
val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
layoutParams.setMargins(ScreenSizeUtils.dp2px(mContext, 12f), 0, 0, 0) //4个参数按顺序分别是左上右下
textView.layoutParams = layoutParams
textView.setOnClickListener {
for (index in 0..pay_way_title_ll.childCount) {
}
}
if (type == 2) {
pay_way_title_ll.addView(textView)
} else {
transport_method_ll.addView(textView)

}
}
 */
object RadioGroupUtil {
    fun addSelectItem(mContext: Context, value: String, mId: Int): RadioButton {
        val radioButton = RadioButton(mContext)
        radioButton.height = ScreenSizeUtils.dp2px(mContext, 90f)
        radioButton.gravity = Gravity.CENTER
        radioButton.id = mId
        radioButton.text = value
        radioButton.setBackgroundResource(R.drawable.blue_gray_select)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            radioButton.setTextColor(mContext.getColorStateList(R.color.blue_gray_select_color))
        } else {
            radioButton.setTextColor(AppCompatResources.getColorStateList(mContext, R.color.blue_gray_select_color))
        }
        val layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT)
//        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(ScreenSizeUtils.dp2px(mContext, 12f), 0, 0, 0) //4个参数按顺序分别是左上右下
        radioButton.layoutParams = layoutParams
        radioButton.setPadding(ScreenSizeUtils.dp2px(mContext, 10f), 0, ScreenSizeUtils.dp2px(mContext, 10f), 0)
        radioButton.setButtonDrawable(android.R.color.transparent);

        return radioButton
    }
}