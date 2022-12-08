package com.base.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    /*日期转成时间戳*/
    public static String dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime() / 1000;
        return String.valueOf(ts);
    }

    //判断是否是周末或者节假日
    public static boolean checkDate() throws Exception {
        boolean result = false;
        //是否是周末
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date bdate = format1.parse(format1.format(System.currentTimeMillis()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /*时间戳转成日期*/
    public static String stampToDate(long milSecond) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(milSecond);
    }

    /*时间戳转成日期*/
    public static String stampToDateYMD(long milSecond) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(milSecond);
    }


    public static String getHours(long second) {//计算秒有多少小时
        long h = 00;
        if (second > 3600) {
            h = second / 3600;
        }
        return h + "";
    }

    public static String getMins(long second) {//计算秒有多少分
        long d = 00;
        long temp = second % 3600;
        if (second > 3600) {
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                }
            }
        } else {
            d = second / 60;
        }

        if (d < 10) {
            return "0" + d;
        } else {
            return d + "";
        }
    }

    public static String getSeconds(long second) {//计算秒有多少秒
        long s = 0;
        long temp = second % 3600;
        if (second > 3600) {
            if (temp != 0) {
                if (temp > 60) {
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        if (s < 10) {
            return "0" + s;
        } else {
            return s + "";
        }
    }
}
