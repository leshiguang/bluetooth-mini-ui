package com.lifesense.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期工具类
 *
 * @author rolandxu
 */
@Deprecated
public class DateUtil {
    protected DateUtil() {
    }

    private static final String TAG = "DateUtil";
    private static final String FORMAT_STRING_VERY_SIMPLE_DATE = "yyyy-MM";
    private static final String FORMAT_STRING_DEFAULT = "yy-MM-dd HH:mm:ss";
    private static final String FORMAT_STRING_SIMPLE_DATE = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_STRING_SIMPLE_DAY = "yyyy-MM-dd";
    private static final String FORMAT_STRING_SIMPLE_DAY2 = "yyyy/MM/dd";

    private static final String FORMAT_STRING_SIMPLE = "yyyy-MM-dd 00:00:00";
    private static final String FORMAT_STRING_DATE_END = "yyyy-MM-dd 23:59:59";
    private static final String FORMAT_STRING_SIMPLE_RUN = "MM月dd日 HH:mm";
    private static final String FORMAT_STRING_SIMPLE_MONTH_DAY_CH = "MM月dd日";
    private static final String FORMAT_STRING_HHMM = "HH:mm";
    private static final String FORMAT_STRING_HHMMSS = "HH:mm:ss";

    private static final String FORMAT_STRING_SIMPLE_YEAR = "yyyy年M月d日 HH:mm";
    private static final String FORMAT_STRING_YEAR = "yyyy年MM月dd日";

    public static SimpleDateFormat getDateDayFormat() {
        SimpleDateFormat simpleDateDayFormat = new SimpleDateFormat(FORMAT_STRING_SIMPLE_DAY);
        return simpleDateDayFormat;
    }

    public static SimpleDateFormat getDateMonthFormat() {
        SimpleDateFormat simpleDateDayFormat = new SimpleDateFormat(FORMAT_STRING_SIMPLE_MONTH_DAY_CH);
        return simpleDateDayFormat;
    }

    public static SimpleDateFormat getWeightMainDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        return simpleDateFormat;
    }

    public static SimpleDateFormat getDayDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat;
    }

    public static SimpleDateFormat getStepHistodayFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        return simpleDateFormat;
    }

    public static SimpleDateFormat getHourFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat;
    }

    public static SimpleDateFormat getHourMinuteFormat() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat;
    }

    // PreciseToDay 精确到天
    private static SimpleDateFormat getSimpleDateFormatPreciseToDay() {
        return new SimpleDateFormat(FORMAT_STRING_SIMPLE, Locale.getDefault());
    }

    private static SimpleDateFormat getSimpleDateFormatNormal() {
        return new SimpleDateFormat(FORMAT_STRING_SIMPLE_DATE, Locale.getDefault());
    }

    /**
     * @param simpleDateFormat 自定义 format
     * @param dateString
     * @return
     */
    public static Date getDate(SimpleDateFormat simpleDateFormat, String dateString) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static SimpleDateFormat getSimpleFormat(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat;
    }

    public static SimpleDateFormat getVeryNormalSimple() {
        return getSimpleFormat(FORMAT_STRING_VERY_SIMPLE_DATE);
    }

    public static SimpleDateFormat getNormalSimple() {
        return getSimpleFormat(FORMAT_STRING_SIMPLE_DATE);
    }

    public static SimpleDateFormat getNormalSimple2() {
        return getSimpleFormat(FORMAT_STRING_SIMPLE_DAY);
    }

    public static SimpleDateFormat getNormalSimple3() {
        return getSimpleFormat(FORMAT_STRING_SIMPLE_DAY2);
    }

    public static Date getNormalToDate(String dateString) {

        return getDate(getNormalSimple(), dateString);
    }


    public static String getDateString(SimpleDateFormat simpleDateFormat, long millisenconds) {
        String dateString = null;
        dateString = simpleDateFormat.format(new Date(millisenconds));
        return dateString;
    }

    public static String getDateString(SimpleDateFormat simpleDateFormat, Date date) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = simpleDateFormat.format(date);
            } catch (Exception e) {

            }
        }

        return dateString;
    }


    public static String getDateString(String format, Date date) {
        return getDateString(new SimpleDateFormat(format), date);
    }

    public static String getDateString(String format, long milliseconds) {
        Date date = new Date(milliseconds);
        return getDateString(new SimpleDateFormat(format), new Date(milliseconds));
    }


    /**
     *
     * @param birthday
     * @return 根据生日返回年龄，生日为空则返回20
     */
    public static int getBirthday(Date birthday) {
        if (birthday == null) {
            return 20;
        }

        Calendar cal = Calendar.getInstance();


        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;


    }

    public static String getCurrentTime() {

        return getDateString(new Date());

    }

    /**
     * @param s
     * @param date 2012-09-09 12:12:22 格式
     * @return
     */
    public static String getDateString(SimpleDateFormat s, String date) {
        String dateString = null;
        dateString = s.format(getDate(getNormalSimple(), date));
        return dateString;
    }

    /**
     * @param
     * @param date 2012-09-09  格式
     * @return
     */
    public static String getDateString(String date) {
        String dateString = null;
        dateString = getDateDayFormat().format(getDate(getNormalSimple2(), date));
        return dateString;
    }

    /**
     * @param
     * @param date 2012-09-09  格式
     * @return
     */
    public static String getDate2String(String date) {
        String dateString = null;
        dateString = getDateDayFormat().format(getDate(getNormalSimple2(), date)) + " 00:00:00";
        return dateString;
    }

    /**
     * 获得当前月的天数
     */

    public static int getDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, false);
        return calendar.get(Calendar.DATE);
    }

    //获取当前时间与某个时间的月份差值
    public static int getMonths(String starttime, String endtime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        int months = 0;
        try {
            begin = sdf.parse(starttime);
            Date end = sdf.parse(endtime);
            months = (end.getYear() - begin.getYear()) * 12 + (end.getMonth() - begin.getMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return months;
    }

    //获得系统当前时间
    public static String getCurrentTimes() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(new Date());
        return formatDate;
    }

    /**
     * 将时间（含时间、分、秒）转换成字符串格式，默认的转换格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @param date 时间
     * @return 返回时间字符串
     */
    public static String getDateString(Date date) {
        return getDateString(date, FORMAT_STRING_SIMPLE_DATE);
    }

    // 根据时间戳 获取字符串
    public static String getDateString(long time) {
        String str = getNormalSimple().format(time);
        return str;
    }

    // 根据时间戳 获取字符串
    public static String getDate2String(long time) {
        String str = getNormalSimple2().format(time);
        return str;
    }

    public static void main(String[] srt) {
        long diftime = -60 * (60 * 1000 * 5);
        int index = Math.round((float) diftime / (60 * 1000) / 5);
        System.out.println(index);

        long current12Time = DateUtil.getStringToLong(DateUtil.getDayOf12clock("2016-04-21 11:04:59", -1, 12));
        long sleepMeasurementDate = DateUtil.getStringToLong("2016-04-21 11:04:59");
        System.out.println(current12Time);
        System.out.println(sleepMeasurementDate);
    }

    /**
     * 根据字符串获取时间戳
     *
     * @param datestr
     * @return
     */
    public static long getStringToLong(String datestr) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING_SIMPLE_DATE);
        return getStringToLong(format, datestr);
    }

    /**
     * 根据字符串获取时间戳
     *
     * @param datestr
     * @return
     */
    public static long getStringToLong(SimpleDateFormat format, String datestr) {
        if (TextUtils.isEmpty(datestr)) {
            return 0;
        }
        Date date = null;
        try {
            date = format.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static long getUTCFromDateString(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = format.parse(date);
        return result.getTime() / 1000;
    }

    /**
     * 根据字符串获取时间戳
     *
     * @param datestr
     * @return
     */
    public static long getString2ToLong(String datestr) {
        if (TextUtils.isEmpty(datestr)) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING_SIMPLE_DAY);
        Date date = null;
        try {
            date = format.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 算出后多少天的多少时 分 秒
     *
     * @param
     * @param day
     * @return
     */
    public static String getDayOf12clock(Date d, int day, int hour, int minute, int second) {
//        Date d = DateUtil.getDate(getNormalSimple(), str);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, day);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute); // 分钟
        calendar.set(Calendar.SECOND, second);
        String datestr = DateUtil.getDateString(calendar.getTime());
//        LSLog.i("tag", "12点datastr: " + datestr);
        return datestr;
    }

    /**
     * @param date   date 格式是 yyyy-MM-dd HH:mm:ss
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static String getDayOf12clock(String date, int day, int hour, int minute, int second) {
        Date d = DateUtil.getDate(getNormalSimple(), date);

        return getDayOf12clock(d, day, hour, minute, second);
    }

    /**
     * 算出后多少天的多少点
     *
     * @param
     * @param day
     * @return
     */
    public static String getDayOf12clock(String str, int day, int hour) {
        Date d = DateUtil.getDate(getNormalSimple(), str);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, day);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        String datestr = DateUtil.getDateString(calendar.getTime());
        LSLog.i("tag", "12点datastr: " + datestr);
        return datestr;
    }

    /**
     * 根据string 获取calendar
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Calendar getCalendarDate(String time) {
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = getNormalSimple().parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将时间转换成字符串格式。
     *
     * @param date    时间
     * @param pattern 转换格式
     * @return String 返回转换后的时间字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateString(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat dateFormate = getSimpleFormat(pattern);

        return dateFormate.format(date);
    }

    /**
     * 判断当前时间是否在时间date之前。
     *
     * @param afterDate
     * @return boolean beforeDate所表示的时间在afterDate所表示的时间之前返回true，否则返回false。
     */
    public static boolean isDateBefore(Date beforeDate, String afterDate) {
        boolean flag = false;
        try {
            SimpleDateFormat formatter = getSimpleFormat(
                    "yyyy-MM-dd HH:mm");
            flag = beforeDate.before(formatter.parse(afterDate));
            return flag;
        } catch (Exception e) {
            return flag;
        }
    }

//    public static void main(String[] args) {
//        boolean d = isDateCorrect(1459818239);
//        System.out.println(d);
//
//    }

    public static boolean isDateCorrect(long measurementDate) {

        boolean isDateCorrect = false;
        Date date = DateUtil.getDate(measurementDate);
        int days = DateUtil.daysBetween(new Date(), date);

        boolean isBefore2014 = DateUtil.isDateBefore(date, "2014-01-01 00:00:00");

        if (!isBefore2014 && days <= 0) {
            isDateCorrect = true;
        }
        return isDateCorrect;

    }


    public static boolean isDateCorrect(String measurementDate) {

        boolean isDateCorrect = false;

        Date date = DateUtil.getNormalToDate(measurementDate);
        if (date == null) {
            return isDateCorrect;
        }
        int days = DateUtil.daysBetween(new Date(), date);

        boolean isBefore2014 = DateUtil.isDateBefore(date, "2014-01-01 00:00:00");

        if (!isBefore2014 && days <= 0) {
            isDateCorrect = true;
        }
        return isDateCorrect;

    }

    /**
     * 返回时间字符串中的日期元素
     *
     * @param dataString 格式必须为:yyyy-MM-dd HH:mm:ss
     * @param type       1:年份;2:月份;5:天;10:小时;12:分钟;13:秒钟
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getStringDateItem(String dataString, String format,
                                        int type) {
        if (!StringUtil.isEmptyOrNull(dataString)) {
            SimpleDateFormat myFmt = getSimpleFormat(
                    format == null ? "yyyy-MM-dd HH:mm:ss" : format);
            try {
                Date date = myFmt.parse(dataString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (type == Calendar.MONTH) {
                    return calendar.get(Calendar.MONTH) + 1;
                } else {
                    return calendar.get(type);
                }
            } catch (ParseException e) {
                LSLog.e("ParseException", e.getMessage());
            }
        }
        return 0;
    }

    /**
     * 获取日期d的年/月/日/时/分/秒前后的一个Date
     *
     * @param d    时间
     * @param num  相差数目
     * @param type 1:年份;2:月份;5:天;10:小时;12:分钟;13:秒钟
     * @return Date
     */
    public static Date getInternalDateAddDate(Date d, int num, int type) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(type, num);
        return now.getTime();
    }

    /**
     * 计算时间差（与当前时间比较）
     *
     * @param date 需要计算时间
     * @return
     * @throws Exception
     * @deprecated 用com.lifesense.utils.DateUtil.stringFromDate()替代
     */
    @SuppressLint("DefaultLocale")
    @Deprecated()
    public static String stringFromDate(Date date) {
        String timeFormat = null;
        if (null != date) {
            Date now = new Date();
            String str = "";
            long time;
            long timeSpan = now.getTime() - date.getTime();
            if (timeSpan < 0)
                timeSpan = -1 * timeSpan;
            //long secondSpan = timeSpan/1000;

            long day = timeSpan / (24 * 60 * 60 * 1000);
            long hour = (timeSpan / (60 * 60 * 1000) - day * 24);
            long min = ((timeSpan / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long second = (timeSpan / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            if (day > 0) {
                if (day > 2) {
                    str = String.format("%d月%d日", date.getMonth(), date.getDate());
                } else if (day > 1) {
                    str = "前天";

                } else if (day > 0) {
                    str = "昨天";
                }
                timeFormat = String.format("%s", str);
            } else {
                if (hour > 0) {
                    str = "小时";
                    time = hour;
                } else if (min > 0) {
                    str = "分钟";
                    time = min;
                } else if (second > 0) {
                    str = "秒";
                    time = second;
                } else {
                    str = "秒";
                    time = 1;
                }
                timeFormat = String.format("%s%s前", String.valueOf(time), str);
            }

        }
        return timeFormat;
    }

    /**
     * 格式化日期
     *
     * @param dateStr 字符型日期
     * @param format  格式
     * @return 返回日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            java.text.DateFormat df = getSimpleFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (Date) df.parse(dt);
        } catch (Exception e) {
            LSLog.e(TAG, String.format("parse date %s with format %s exception", dateStr, format), e);
        }
        return date;
    }

    /**
     * 格式化日期:yyyy/MM/dd
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 将Unix时间转化为java时间
     *
     * @param timestampString unix时间戳字符串(秒)
     * @return Date
     */
    public static Date getDate(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        Date date = new java.util.Date(timestamp);
        return date;
    }

    /**
     * 将Unix时间转化为java时间
     *
     * @param timestamp unix时间戳(秒)
     * @return Date
     */
    public static Date getDate(long timestamp) {
        timestamp = timestamp * 1000;
        Date date = new java.util.Date(timestamp);
        return date;
    }

    /**
     * @param timestamp 毫渺
     * @return
     */
    public static Date getDateBymill(long timestamp) {
        Date date = new Date(timestamp);
        return date;
    }

    /***
     * 获取程序运行时距离1970.1.1 00：00：00（utc）的秒数
     *
     * @return
     */
    public static long getCurrentTimeInSeconds() {
        long time_by_ms = 0;
        time_by_ms += System.currentTimeMillis() / 1000;
        return time_by_ms;
    }

    /**
     * 获取程序运行时距离1970.1.1 00：00：00（utc）的秒数
     *
     * @param date 要计算的时间
     * @return
     */
    public static long getTimeInSeconds(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime() / 1000;
    }

    public static SimpleDateFormat getMonthFormat() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("M月d日");
        return monthFormat;
    }

    public static SimpleDateFormat getMonthFormat2() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd");
        return monthFormat;
    }

    public static SimpleDateFormat getMonthFormat3() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("M月");
        return monthFormat;
    }

    /**
     * 把Date 转成 1月3日格式
     *
     * @param date
     * @return
     */
    public static String getMonthDay(Date date) {
        String str = new SimpleDateFormat("MM/dd").format(date);
        return str;
    }

    /**
     * 把Date 转成 1月3日格式
     *
     * @param srt
     * @return
     */
    public static String getMonthDay(String srt) {
        String str = new SimpleDateFormat("MM/dd").format(getDate(getNormalSimple(), srt));
        return str;
    }

    /**
     * 把Date 转成 1/3格式
     *
     * @param date
     * @return
     */
    public static String getMonthDay2(String date) {
        return new SimpleDateFormat("MM/dd").format(getDate(getNormalSimple(), date));
    }

    public static String getMonthDayHourMin(Date date) {
        return new SimpleDateFormat("M月d日 HH:mm").format(date);
    }

    public static String getMonth(String date) {
        return new SimpleDateFormat("M月").format(getDate(getNormalSimple(), date));
    }

    public static String getMonth(Date date) {
        return new SimpleDateFormat("M月").format(date);
    }

    public static String getWeek(Date date) {
        Date date1 = getDayStartWeekTime(date);
        Date date2 = getDayEndWeekTime(date);

        return getMonthFormat2().format(date1) + "-" + getMonthFormat2().format(date2);
    }

    public static String getWeek(String date) {
        Date date1 = getDayStartWeekTime(date);
        Date date2 = getDayEndWeekTime(date);

        return getMonthFormat2().format(date1) + "-" + getMonthFormat2().format(date2);
    }

    /**
     * 把Date 转成 1/3格式
     *
     * @param date
     * @return
     */
    public static String getMonthDay2(Date date) {
        return getMonthFormat2().format(date);
    }

    public static boolean isBetweenDate(Date startDate, Date endDate, Date currentDate) {
        long currentDateTime = currentDate.getTime();
        if (currentDateTime >= startDate.getTime() && currentDateTime < endDate.getTime()) {
            return true;
        }
        return false;
    }

    public static boolean isBetweenMonth(String month, Date currentDate) {
        String currentMonth = getDayBelongToMonth(currentDate);
        if (currentMonth.equals(month)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间所属月份
     *
     * @param date
     * @return
     */
    public static String getDayBelongToMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        String result = DateUtil.getDateString(getVeryNormalSimple(), currentDate.getTime());
        return result;
    }

    /**
     * 获取当月开始时间
     *
     * @param srt
     * @return
     */
    public static String getDayStartMonthTime(String srt) {
        Date date = getDate(getNormalSimple(), srt);
        return getDayBelongToMonth(date);
    }

    /**
     * 获取当周开始时间
     *
     * @param srt
     * @return
     */
    public static Date getDayStartWeekTime(String srt) {
        Date date = getDate(getNormalSimple(), srt);
        return getDayStartWeekTime(date);
    }


    public static Date getDayEndWeekTime(Date date) {

        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.setTime(date);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        currentDate.add(Calendar.DATE, 7);//加7天
        currentDate.add(Calendar.SECOND, -1);//减1秒
        return currentDate.getTime();
    }

    public static Date getDayEndWeekTime(String srtDate) {
        Date date = getDate(getNormalSimple(), srtDate);
        return getDayEndWeekTime(date);
    }


    /**
     * 获取当周开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStartWeekTime(Date date) {

        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.setTime(date);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
    }


    public static String[] getDateFromSTE(String start, String end) {

        try {
            Date startDate = getSimpleFormat(FORMAT_STRING_SIMPLE_DATE).parse(start);
            Date endDate = getSimpleFormat(FORMAT_STRING_SIMPLE_DATE).parse(end);
            int between = daysBetween(startDate, endDate);
            String[] str = new String[between + 1];
            Calendar cal = Calendar.getInstance();

            cal.setTime(startDate);
            for (int i = 0; i < str.length; i++) {
                str[i] = getSimpleFormat(FORMAT_STRING_SIMPLE_DATE).format(cal.getTime()).split(" ")[0];
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }

            return str;

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        String[] str = new String[1];
        return str;
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setTime(date2);

        boolean isSameDate = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        return isSameDate;
    }

    public static int daysBetween(Date date1, Date date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        int day1 = cal.get(Calendar.DAY_OF_YEAR);
        int year1 = cal.get(Calendar.YEAR);

        cal.setTime(date2);
        int day2 = cal.get(Calendar.DAY_OF_YEAR);
        int year2 = cal.get(Calendar.YEAR);

        int result = 0;
        if (year1 == year2) {
            result = day2 - day1;
        } else {
            result = daysInDifferenceYearBetween(date1, date2);
        }

        return result;

    }

    public static int daysInDifferenceYearBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);

        long time2 = cal.getTimeInMillis();
        float between_days = (float) ((time2 - time1) / (1000.0 * 3600.0 * 24.0));
        LSLog.i("daysBetween", "formatTime,,=between_days=" + between_days);
        if (0 < between_days && between_days < 1) {
            return 0;
        } else {
            return (int) Math.floor(between_days);
        }

    }


    /**
     * 下一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Date result = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curMonthday = calendar.get(Calendar.DAY_OF_MONTH);
        curMonthday = curMonthday + 1;
        calendar.set(Calendar.DAY_OF_MONTH, curMonthday);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));
        result = calendar.getTime();

        return result;
    }

    public static long getDayStart(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getDayEnd(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /***
     * 获取当天0点时间
     *
     * @param date
     * @return
     */
    public static Date get0ClockDay(Date date) {
        return getDay(date, 0, 0, 0);
    }

    /**
     * 指定date的时分秒
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getDay(Date date, int hour, int minute, int second) {
        Date result;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);

        result = calendar.getTime();

        return result;
    }

    /**
     * 下一天 指定时分秒
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getNextDay(Date date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curMonthday = calendar.get(Calendar.DAY_OF_MONTH);
        curMonthday = curMonthday + 1;
        calendar.set(Calendar.DAY_OF_MONTH, curMonthday);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();

        return result;
    }

    public static Date getSettingDay(Date date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();
        return result;
    }

    /**
     * 根据时间间隔，指定时分秒获取时间
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getNextDay(int dayInterval, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int curMonthday = calendar.get(Calendar.DAY_OF_MONTH);
        curMonthday = curMonthday + dayInterval;
        calendar.set(Calendar.DAY_OF_MONTH, curMonthday);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();

        return result;
    }

    /**
     * 根据时间间隔，指定时分秒获取时间
     *
     * @param dayInterval
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long getNextDayTime(int dayInterval, int hour, int minute, int second) {
        Date date = getNextDay(dayInterval, hour, minute, second);
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }


    /**
     * 获取当天时间，指定时分秒
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getCurrentDay(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天时间，指定时分秒
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long getCurrentDayTime(int hour, int minute, int second) {
        Date date = getCurrentDay(hour, minute, second);
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 获取上一个月的时间
     *
     * @param date
     * @return
     */
    public static Date getLastMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DAY_OF_YEAR, -1); //减一天
        //设置为23:59:59
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取上5周的时间
     *
     * @param date
     * @return
     */
    public static Date getLast5WeekDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        return cal.getTime();
    }

    /**
     * 判断2个时间差是否大于1天
     *
     * @return true 相差了一天 false 没相差
     */
    public static boolean dateDiffGtOne(Date date1, Date date2) {
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTime(date1);

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date2);

        if (oldCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR)) {
            if (oldCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR)) {
                return false;  //同一天
            }

        }
        return true;
    }

    public static String gethour(String time) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_STRING_SIMPLE_DATE);
        Date date = DateUtil.getDate(format, time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.add(Calendar.SECOND, 1);
        date = calendar.getTime();
        String str = (DateUtil.getSimpleFormat("HH:mm")).format(date);
        return str;
    }


    public static String getCustomDateString(Date measureDate, String format) {
        SimpleDateFormat df_month_date = new SimpleDateFormat(format);
        Date currentDate = new Date();
        int daysBetween = DateUtil.daysBetween(currentDate, measureDate);
        String time = "";
        if (daysBetween == 0) {
            time = "今天";
        } else if (daysBetween == -1) {
            time = "昨天";
        } else if (daysBetween == -2) {
            time = "前天";
        } else {
            time = df_month_date.format(measureDate);
        }
        return time;
    }

    /**
     * String 转date
     *
     * @param time
     * @return
     */
    public static Date stringToDate(String time) {
        Date date = null;
        try {
            date = getNormalSimple().parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String 转date
     *
     * @param time
     * @return
     */
    public static Date string2ToDate(String time) {
        Date date = null;
        try {
            date = getNormalSimple2().parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 查找起始和结束时间相差多少天
     *
     * @return
     */
    public static List<Date> findDates(String dBeginStr, String dEndStr) {

        Date dBegin = stringToDate(dBeginStr);
        Date dEnd = stringToDate(dEndStr);
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);


        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后

        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 判断是否是当天
     *
     * @param time
     * @return
     */
    public static boolean isCurrentDay(long time) {
        // 当天的0点
        long currentTime = DateUtil.getStringToLong(DateUtil
                .getDayOf12clock(DateUtil.getDateString(System.currentTimeMillis()), 0, 0));
        // 第二天0点
        long nextDaytime = DateUtil.getStringToLong(
                DateUtil.getDayOf12clock(DateUtil.getDateString(System.currentTimeMillis()), 1, 0));
        // 判断当天是否有数据
        // 判断第一条数据分析时间是否在昨天的00和第二天的12:00
        if (currentTime <= time && time < nextDaytime) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取2个日期，共占了几周
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getWeeksBetween(Date startDate, Date endDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int startYear = calendar.get(Calendar.YEAR);
        int startWeekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (startWeekIndex == 1) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int startWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.setTime(endDate);
        int endYear = calendar.get(Calendar.YEAR);
        int endWeekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (endWeekIndex == 1) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int endtWeekOfYeah = calendar.get(Calendar.WEEK_OF_YEAR);

        int weekCount = 0;
        if (endYear > startYear) {
            weekCount = (endYear - startYear - 1) * 12;
        }
        LSLog.i("getWeeksBetween", "startWeekOfYear=" + startWeekOfYear);
        LSLog.i("getWeeksBetween", "endtWeekOfYeah=" + endtWeekOfYeah);

        if (endtWeekOfYeah > startWeekOfYear) {

            weekCount += endtWeekOfYeah - startWeekOfYear + 1;

        } else if (endtWeekOfYeah != startWeekOfYear) {

            weekCount += endtWeekOfYeah + 48 - startWeekOfYear + 1;
        }

        LSLog.i("getWeeksBetween", "startDate=" + startDate);
        LSLog.i("getWeeksBetween", "endDate=" + endDate);

        return weekCount;
    }

    public static boolean isBetweenMonthDate(Date date, Date currentDate) {

        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        if (currentCal.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)) {
            return true;
        }

        return false;
    }

    /**
     * @param startTime 格式是yyyy-MM-dd HH:mm:ss
     * @param endTime
     * @return
     */
    public static int getTimeDiffDay(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return 0;
        }
        Date startDate = getNormalToDate(startTime);
        Date endDate = getNormalToDate(endTime);
        return getTimeDiffDay(startDate, endDate);
    }

    /**
     * 判断2个日期相差多少天
     *
     * @return
     */
    public static int getTimeDiffDay(Date startDate, Date endDate) {
        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        startDateCal.set(Calendar.HOUR_OF_DAY, 0);
        startDateCal.set(Calendar.MINUTE, 0);
        startDateCal.set(Calendar.SECOND, 0);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);
        endDateCal.set(Calendar.HOUR_OF_DAY, 0);
        endDateCal.set(Calendar.MINUTE, 0);
        endDateCal.set(Calendar.SECOND, 0);
        long diff = Math.abs(endDateCal.getTimeInMillis() - startDateCal.getTimeInMillis());
        long days = diff / (1000 * 60 * 60 * 24);

        return (int) days;
    }

    /**
     * 判断2天是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断是否同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        return isSameYear;
    }

    /**
     * 获取当天0点开始时间的时间戳
     * created by tim.hu 2016-11-30
     *
     * @return
     */
    public static long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    /**
     * 传入long类型的时间
     *
     * @return返回字符串格式 yyyy-MM-dd 00:00:00
     */
    public static String getLongToStringPreciseToDay(long time) {
        if (time == 0) {
            return null;
        }
        return getSimpleDateFormatPreciseToDay().format(time);
    }

    /**
     * 传入long类型的时间
     *
     * @return返回Date格式 Thu Dec 22 00:00:00 GMT+08:00 2016
     */
    public static Date getStringToDatePreciseToDay(long time) {
        String longToString = getLongToStringPreciseToDay(time);
        if (time == 0 || longToString == null) {
            return null;
        }
        try {
            return getSimpleDateFormatPreciseToDay().parse(longToString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入long类型的时间
     *
     * @return返回字符串格式 返回精确到天;如 2016-12-22的long
     */
    public static Long getLongToLongPreciseToDay(long time) {
        if (time == 0) {
            return null;
        }
        Date stringToDatePreciseToDay = getStringToDatePreciseToDay(time);
        if (stringToDatePreciseToDay == null) {
            return null;
        }
        return stringToDatePreciseToDay.getTime();
    }

    /**
     * 传入String类型的任意时间
     *
     * @return返回Long格式 返回精确到天;如 2016-12-22的long
     */
    public static Long getStringToLongPreciseToDay(String time) {
        Long simpleLong = null;
        if (time == null) {
            return null;
        }
        try {
            simpleLong = getSimpleDateFormatNormal().parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (simpleLong == null) {
            return null;
        }
        return getLongToLongPreciseToDay(simpleLong);
    }

    /**
     * 5点未分界线，大于当天5点的，获取下一天的时间戳，小于当天5点的，获取当天时间戳
     *
     * @return
     */
    public static long getCheckTime() {
        long currentDayTime = DateUtil.getCurrentDayTime(5, 0, 0);
        long currentTimeMillis = System.currentTimeMillis();
        long time = 0;
        Long longToLongPreciseToDay = DateUtil.getLongToLongPreciseToDay(currentTimeMillis);
        if (longToLongPreciseToDay == null) {
            return 0;
        }
        if (currentTimeMillis > currentDayTime) {
            Date date = new Date(longToLongPreciseToDay);
            Date nextDay = DateUtil.getNextDay(date);
            time = nextDay.getTime();
        } else {
            time = longToLongPreciseToDay;
        }
        return time;
    }

    public static void printfWeeks(String date) throws Exception {
        // String date = "2013-09";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date1 = dateFormat.parse(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("days:" + days);
        int count = 0;
        for (int i = 1; i <= days; i++) {
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = dateFormat1.parse(date + "-" + i);
            calendar.clear();
            calendar.setTime(date2);
            int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
            if (k == 1) {// 若当天是周日
                count++;
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                if (i - 6 <= 1) {
                    System.out.println("本周开始日期:" + date + "-" + 1);
                } else {
                    System.out.println("本周开始日期:" + date + "-" + (i - 6));
                }
                System.out.println("本周结束日期:" + date + "-" + i);
                System.out.println("-----------------------------------");
            }
            if (k != 1 && i == days) {// 若是本月最好一天，且不是周日
                count++;
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                System.out.println("本周开始日期:" + date + "-" + (i - k + 2));
                System.out.println("本周结束日期:" + date + "-" + i);
                System.out.println("-----------------------------------");
            }
        }
    }

    public static int getWeekDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static String getSleepMonth2Day(Date date) {
        String str = DateUtil.getMonthDay2(date);
        long diff = 432000000L;
        if (System.currentTimeMillis() - date.getTime() > diff) { //4天
            return str;
        }

        if (str.equals(DateUtil.getMonthDay2(new Date()))) {
            str = "昨晚";
        } else {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -1);
            if (str.equals(DateUtil.getMonthDay2(cal.getTime()))) {
                str = "前晚";
            }
        }
        return str;
    }


    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getStartMonthDate(SimpleDateFormat sdf, String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    public static String getStartMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getSimpleDateFormatNormal().parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("==calendar.getTime()==" + calendar.getTime());
        return getSimpleDateFormatNormal().format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getEndMonthDate(SimpleDateFormat sdf, String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    public static String getEndMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getSimpleDateFormatNormal().parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getSimpleDateFormatNormal().format(calendar.getTime());
    }

    public static long getUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        return unixTime;
    }

}
