package com.mbcq.baselibrary.util.system;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;


import com.mbcq.baselibrary.util.log.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
public class TimeUtils {
    //毫秒 获取当前时间0点
    public static long getTime000(long time) {
        return time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
    }

    //秒 获取当前时间0点
    @SuppressLint("DefaultLocale")
    public static long getTime(long time) {
        return getTime000(time) / 1000;
    }

    //24 HH 12hh
    public static String timestampToDateStrHH(Long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String format = dateFormat.format(date);
        return format;
    }

    public static String timestampToDateStrMM(Long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("mm");
        String format = dateFormat.format(date);
        return format;
    }

    public static String timestampToDateStrSS(Long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("ss");
        String format = dateFormat.format(date);
        return format;
    }

    public static String getCurrentHHMMSS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
   public static String getCurrent() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentHHMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentYYMMDD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentYY() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentDD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getMongoDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String s = format.format(new Date());
        return s;
    }

    public static String utcTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'kk':'mm':'ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("utc"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    public static Date utc2local(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("utc"));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LogUtils.e(e.getCause() + e.getMessage());
            e.printStackTrace();
        }
        return date;
    }

    public static String getUTCCurrHours() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.HOUR_OF_DAY);
        return String.valueOf(i);
    }

    public static String getUTCCurrHours(long time) {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.HOUR_OF_DAY);
        return String.valueOf(i);
    }

    public static String getUTCCurrMinute() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.MINUTE);
        return String.valueOf(i);
    }

    public static String getUTCCurrSecond() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.SECOND);
        return String.valueOf(i);
    }

    public static long getZoreTimeMs() {
        Calendar zoreDate = Calendar.getInstance();
        zoreDate.set(Calendar.HOUR_OF_DAY, 0);
        zoreDate.set(Calendar.MINUTE, 0);
        zoreDate.set(Calendar.SECOND, 0);
        zoreDate.set(Calendar.MILLISECOND, 0);
        return zoreDate.getTimeInMillis();
    }

    public static long getUTCZoreTimeMs() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //zoreDate.setTimeZone();
        //zoreDate.setTimeInMillis(System.currentTimeMillis());
        zoreDate.set(Calendar.HOUR_OF_DAY, 0);
        zoreDate.set(Calendar.MINUTE, 0);
        zoreDate.set(Calendar.SECOND, 0);
        zoreDate.set(Calendar.MILLISECOND, 0);
        return zoreDate.getTimeInMillis();
    }

    public static long getUTCCurrMs() {
        Calendar zoreDate = Calendar.getInstance();
        zoreDate.setTimeZone(TimeZone.getTimeZone("utc"));
        return zoreDate.getTimeInMillis();
    }

    public static String getCurrTimeZone() {
        return TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT);
    }

    public static String getCurrTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static String getCurrTime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }


    public static void setTime(Context context, int y, int month, int d, int h, int m, int s, int mill) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, d);
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE, m);
        c.set(Calendar.SECOND, s);
        c.set(Calendar.MILLISECOND, mill);

        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            try {
                ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
            } catch (Exception e) {
                LogUtils.e(e.getMessage() + e.getCause());
            }
        }
    }

    /**
     * @param context
     * @param zone    GMT+8:00
     */
    public static void setTimeZone(Context context, String zone) {
        try {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTimeZone(zone);
        } catch (Exception e) {
            LogUtils.e(e.getMessage() + e.getCause());
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate1(long timeMillis) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");

        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间
     * 秒 timeMillis
     */
    public static String stampToDate2(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间
     * 毫秒 timeMillis
     */
    public static String stampToDatemm2(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(timeMillis * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 获得指定日期的年份
     *
     * @param date
     * @return
     */

    public static int getYearByDate(Date date) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获得指定日期的月份
     *
     * @param date
     * @return
     */

    public static int getMonthByDate(Date date) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        return cal.get(Calendar.MONTH)+1;

    }


    private static Calendar gregorianCalendar = Calendar.getInstance();

    /**
     * 获取日期星期一日期
     *
     * @param
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期天日期
     *
     * @param
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }


    /**
     * 得到当前日期的截取年,月,日
     * i = 0年;i=1月;i=2日;
     */

    public String subStringData(int i, String data) {
        String[] split = data.split("-");

        return split[i];

    }

    /**
     * 去除月,日前面的0
     */

    public String deleteDate(String date) {
        if (date.substring(0, 1).equals("0")) {
            Log.e("TAG", date.substring(1, 2));
            return date.substring(1, 2);
        } else {
            return date;
        }
    }


    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {

        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 求某一个时间向前多少秒的时间(currentTimeToBefer)---OK
     *
     * @param givedTime        给定的时间
     * @param interval         间隔时间的毫秒数；计算方式 ：n(天)*24(小时)*60(分钟)*60(秒)(类型)
     * @param format_Date_Sign 输出日期的格式；如yyyy-MM-dd、yyyyMMdd等；
     */
    public static String givedTimeToBefer(String givedTime, long interval, String format_Date_Sign) {
        String tomorrow = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_Date_Sign);
            Date gDate = sdf.parse(givedTime);
            long current = gDate.getTime(); // 将Calendar表示的时间转换成毫秒
            long beforeOrAfter = current - interval * 1000L; // 将Calendar表示的时间转换成毫秒
            Date date = new Date(beforeOrAfter); // 用timeTwo作参数构造date2
            tomorrow = new SimpleDateFormat(format_Date_Sign).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tomorrow;
    }

    /**
     * <<<<<<< HEAD
     * 比较两个日期先后
     */

    public static boolean compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        boolean b = false;
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                b = true;
            } else if (dt1.getTime() < dt2.getTime()) {
                b = false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return b;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return String
     */
    public static String getWeekOfDateString(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return int
     */
    public static int getWeekOfDateInt(Date date) {
        int[] weekDaysCode = {0, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }

    /**
     * 根据给定某个月日期得到之间差得天数
     *
     * @param endTime
     * @param startTime
     * @return
     */
    public int getMonthDay(String endTime, String startTime) {
        int i = 0;
        try {
            i = Integer.valueOf(endTime.substring(8, 10)).intValue() - Integer.valueOf(startTime.substring(8, 10)).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return i + 1;
    }

    /**
     * 分钟转换hour
     *
     * @param minutes
     * @return
     */
    public int exchangeHour(int minutes) {
        int hour = 344 / 60;

        return hour;
    }

    /**
     * 分钟转换minute
     *
     * @param minutes
     * @return
     */
    public int exchangeMinute(int minutes) {
        int minute = 344 % 60;

        return minute;
    }


}
