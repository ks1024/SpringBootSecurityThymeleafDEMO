package com.code4jdemo.sbsst.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Long getTime() {
        return time2int(new Date());
    }

    public static Long time2int(Date date) {
        long time = date.getTime() /1000L;
        return time;
    }

    public static long diffMin(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
        return diff / (60 * 1000) % 60;
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeString = dataFormat.format(date);
        return timeString;
    }
}
