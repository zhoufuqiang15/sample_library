package com.abount.cg.commonlibrary.utils;

import android.os.Parcel;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mo_yu on 18/2/22.时间工具类
 */
public class TimeUtils {

    private static long mServerTimeOffset = 0l; //本地时间与服务器时间偏移

    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_MIDDLE = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String DATE_FORMAT_LONG_SIMPLE = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy年MM月";

    private static final int SEVER_TIME_ZONE_OFFSET = TimeZone.getTimeZone("GMT+8")
            .getRawOffset();


    /**
     * 设置系统时间与本地时间偏移量
     *
     * @param serverTime 服务器时间戳
     */
    public static void setTimeOffset(long serverTime) {
        mServerTimeOffset = serverTime - System.currentTimeMillis();
    }

    public static String time2UtcString(Date date) {
        DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_LONG, Locale.getDefault());
        TimeZone pst = TimeZone.getTimeZone("UTC+0");
        dateFormatter.setTimeZone(pst);
        return dateFormatter.format(date);
    }


    /**
     * 时间解析校准
     * <p>
     * DateTime 解析时间为本地时区 服务器时间为东八区时间
     * <p>
     * 如 2016-11-11 12：00：00
     * <p>
     * DateTime 2016-11-11 12：00：00 +zone
     * <p>
     * ServerTime 2016-11-11 12：00：00 +8:00
     * <p>
     * 两边时间会出现偏差需要对时间进行转换
     *
     * @param dateTime 自动解析的时间
     * @return 校准后的时间
     */
    public static DateTime timeServerTimeZone(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return new DateTime(dateTime.getMillis() + dateTime.getZone()
                .toTimeZone()
                .getRawOffset() - SEVER_TIME_ZONE_OFFSET);
    }


    /**
     * 获取当前服务器时间 多用于倒计时
     *
     * @return 本地时间加上与服务器的时间差
     */
    public static long getServerCurrentTimeMillis() {
        return System.currentTimeMillis() + mServerTimeOffset;
    }

    /**
     * 计算制定date和现在之间的时间差
     *
     * @param dateTime
     * @return
     */
    public static boolean isWedding(DateTime dateTime) {
        long cur_time = Calendar.getInstance()
                .getTimeInMillis();
        long time = dateTime.getMillis();

        long del_time = time - cur_time;
        if (del_time > 0) {
            return true;
        }
        return false;
    }

    public static boolean isThreadNew(DateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        long cur_time = Calendar.getInstance()
                .getTimeInMillis();
        long time = dateTime.getMillis();

        long del_time = cur_time - time;
        if (del_time < 12 * 60 * 60 * 1000) {
            return true;
        }
        return false;
    }

    /**
     * 持续的时间 mm:ss 格式 显示
     *
     * @param duration
     * @return
     */
    public static String formatForDurationTime(long duration) {
        int timeInSeconds = (int) (duration / 1000);
        int seconds = timeInSeconds % 60;
        int minutes = (timeInSeconds / 60) % 60;
        int hours = timeInSeconds / 3600;
        Formatter mFormatter = new Formatter();
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds)
                    .toString();
        }
    }


    public static void writeDateTimeToParcel(Parcel dest, DateTime dateTime) {
        dest.writeLong(dateTime == null ? 0 : dateTime.getMillis());
    }

    public static DateTime readDateTimeToParcel(Parcel in) {
        long millis = in.readLong();
        if (millis > 0) {
            return new DateTime(millis);
        }
        return null;
    }

    public static boolean isSameDay(DateTime date, DateTime date2) {
        if (date != null && date2 != null) {
            return date.getYear() == date2.getYear() && date.getMonthOfYear() ==
                    date2.getMonthOfYear() && date.getDayOfMonth() == date2.getDayOfMonth();
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static ArrayList<Integer> convertDateToDays(List<String> dates) {
        ArrayList<Integer> list = null;
        if (!CommonUtil.isCollectionEmpty(dates)) {
            list = new ArrayList<>();
            for (String date : dates) {
                String[] dateStrs = date.split("-");
                if (dateStrs.length >= 2) {
                    list.add(Integer.valueOf(dateStrs[2]));
                }
            }
        }
        return list;
    }

    /**
     * 获得剩余天数
     *
     * @return -1 endTime =null 或者给定时间在服务器时间之前
     */
    public static int getSurplusDay(DateTime endTime) {
        if (endTime == null) {
            return -1;
        }
        DateTime endDateTime = new DateTime(endTime).withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59);
        long dis = endDateTime.getMillis() - TimeUtils.getServerCurrentTimeMillis();
        if (dis > 0) {
            long hours = dis / (60 * 60 * 1000);
            int days = (int) Math.ceil((float) hours / 24L);
            return days;
        }

        return -1;
    }


    public static int getSurplusDay(Date date) {
        if (date == null) {
            return -1;
        }
        return getSurplusDay(new DateTime(date));
    }

}

