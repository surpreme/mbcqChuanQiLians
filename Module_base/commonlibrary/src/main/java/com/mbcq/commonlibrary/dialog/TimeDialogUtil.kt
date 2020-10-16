package com.mbcq.commonlibrary.dialog

import android.content.Context
import android.graphics.Color
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import java.util.*

object TimeDialogUtil {
    /**
     * 时间选择器
     * https://github.com/Bigkoo/Android-PickerView
    .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
    .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
    .setSubmitText("确定")//确定按钮文字
    .setCancelText("取消")//取消按钮文字
    .setTitleText("请选择")//标题
    .setSubCalSize(18)//确定和取消文字大小
    .setTitleSize(20)//标题文字大小
    .setTitleColor(Color.GREEN)//标题文字颜色
    .setSubmitColor(Color.GREEN)//确定按钮文字颜色
    .setCancelColor(Color.GREEN)//取消按钮文字颜色
    .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
    .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
    .isCyclic(false)//是否循环滚动
    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
    .setDividerColor(Color.YELLOW)//设置分割线的颜色
    .setTextColorCenter(Color.RED)//设置选中项的颜色
    .setTextColorOut(Color.BLUE)//设置没有被选中项的颜色
    .setContentSize(21)//滚轮文字大小
    .setDate(selectedDate) 如果不设置的话，默认是系统时间
    .setLineSpacingMultiplier(1.2f)//设置两横线之间的间隔倍数
    .setTextXOffset(-10, 0, 10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
    .setRangDate(startDate, endDate)起始终止年月日设定
    .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
    .setDecorView(null)//设置要将pickerview显示到的容器id 必须是viewgroup
    .isDialog(false)//是否显示为对话框样式
     */

    fun getChoiceTimer(mContext: Context, listener: OnTimeSelectListener, title: String, isStartCurrentTime: Boolean, isEndCurrentTime: Boolean, isYear: Boolean, isHM: Boolean): TimePickerView {
        return getChoiceTimer(mContext, listener, title, isStartCurrentTime, isEndCurrentTime, isYear, isHM, true)
    }


    fun getChoiceTimer(mContext: Context, listener: OnTimeSelectListener, title: String, isStartCurrentTime: Boolean, isEndCurrentTime: Boolean, isYear: Boolean, isHM: Boolean, isDialog: Boolean): TimePickerView {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        val selectDate = Calendar.getInstance()
        selectDate.time = Date(System.currentTimeMillis())

        if (isStartCurrentTime)
            startDate.time = Date(System.currentTimeMillis())
        else
            startDate.set(2010, 1, 1)
        if (isEndCurrentTime)
            endDate.time = Date(System.currentTimeMillis())
        else
            endDate.set(2050, 11, 30)
        return TimePickerBuilder(mContext, listener)
                .setType(booleanArrayOf(true, true, isYear, isHM, isHM, false)) // 默认全部显示
                .setCancelText("取消") //取消按钮文字
                .setSubmitText("确定") //确认按钮文字
                .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(Color.BLACK) //标题文字颜色
                .setSubmitColor(Color.BLUE) //确定按钮文字颜色
                .setCancelColor(Color.GRAY) //取消按钮文字颜色
                .setTextColorCenter(Color.BLUE) //设置选中项的颜色
                .setRangDate(startDate, endDate) //起始终止年月日设定
                .setDate(selectDate) //设置默认选中时间
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTitleText(title)
                .isDialog(isDialog) //是否显示为对话框样式
                .build()
    }
}