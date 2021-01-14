package com.mbcq.commonlibrary

import android.annotation.SuppressLint
import com.mbcq.baselibrary.util.system.TimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FilterTimeUtils {
    fun getStartTime(lastDay: Int): String {
        return "${TimeUtils.getLastdayStr(lastDay)} 00:00:00"
    }

    @SuppressLint("SimpleDateFormat")
    fun getEndTime(): String {
        val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val mDate = Date(System.currentTimeMillis())
        val format = mDateFormat.format(mDate)
        return "$format 23:59:59"
    }
}