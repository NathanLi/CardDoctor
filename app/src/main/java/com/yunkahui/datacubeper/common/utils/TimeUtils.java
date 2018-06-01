package com.yunkahui.datacubeper.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.http.FormUrlEncoded;

public class TimeUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_PATTERN_WITH_HM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_PATTERN_WITH_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final int DAY_MILLIS = 1000 * 1 * 60 * 60 * 24;

    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static String format(String pattern, long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CANADA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String timeStr = dateFormat.format(calendar.getTime());
        return timeStr;
    }

    public static long getTimeStampByDate(String format, String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String addZero(int month) {
        String s = month + "";
        return s.length() == 1 ? "0" + s : s;
    }
}
