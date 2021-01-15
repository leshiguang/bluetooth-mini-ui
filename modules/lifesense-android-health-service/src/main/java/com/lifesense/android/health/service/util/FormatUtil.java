package com.lifesense.android.health.service.util;

/**
 * Create by qwerty
 * Create on 2020/11/5
 **/
public class FormatUtil {


    public static String formatTime(int hour, int min) {
        return formatNum(hour) + ":" + formatNum(min);
    }

    public static String formatNum(int num) {
        String hourStr = num + "";
        return hourStr.length() < 2 ? "0" + hourStr : hourStr;
    }
}
