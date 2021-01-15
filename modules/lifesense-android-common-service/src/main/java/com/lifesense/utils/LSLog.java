package com.lifesense.utils;


import android.content.Context;
import android.util.Log;

import com.lifesense.commonlogic.BuildConfig;
import com.lifesense.commonlogic.log.LogConstant;
import com.lifesense.commonlogic.log.LogInitInfo;
import com.lifesense.commonlogic.log.LoggerManager;
import com.lifesense.commonlogic.log.ZipLogAction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日志工具
 *
 * @author rolandxu
 */
public class LSLog {
    //输出日志的级别,大于等于此级别的日志将会输出
    protected static int level = Log.DEBUG;
    //日志标识
    protected static String logMark = "LSLog";

    public static Map<String, Long> millionSeconndStartMap = new HashMap<String, Long>();

    private static LogInitInfo sLogInitInfo = null;
    private static boolean sIsLogAllOpen = false;

    public static void  initLog(Context context,String fileName) {
        LogInitInfo initInfo = LoggerManager.createDefaultInitInfo(context);
        if(initInfo == null) {
            return;
        }
        sLogInitInfo = initInfo;
        sLogInitInfo.setFileName(fileName);
        LoggerManager.initLogInfo(initInfo);
        setDefaultLogLevel();
        sIsLogAllOpen = false;
    }

    public static boolean reversalLogLevel() {
        if(sIsLogAllOpen) {
            setDefaultLogLevel();
        } else {
            LoggerManager.resetLogLevel(LogConstant.LEVEL_VERBOSE, LogConstant.LEVEL_VERBOSE);
        }
        sIsLogAllOpen = !sIsLogAllOpen;
        return sIsLogAllOpen;
    }

    private static void setDefaultLogLevel() {
        if (BuildConfig.DEBUG) {
            LoggerManager.resetLogLevel(LogConstant.LEVEL_VERBOSE, LogConstant.LEVEL_VERBOSE);
        } else {
            LoggerManager.resetLogLevel(LogConstant.LEVEL_NONE, LogConstant.LEVEL_ERROR);
        }
    }

    public static void requestZipLogFile(ZipLogAction.OnZipResultListener callback) {
        if(sLogInitInfo == null) {
            if(callback != null) {
                callback.onResult(false,null);
            }
            return;
        }
        try {
            File dirFile = new File(sLogInitInfo.getFileDir());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                    Locale.getDefault());
            String zipFileName = format.format(new Date(System.currentTimeMillis())) + ".zip";
            String zipFilePath = dirFile.getParent() + "/" + zipFileName;
            ZipLogAction zipLogAction = new ZipLogAction(callback,zipFilePath);
            LoggerManager.zipLogFile(zipLogAction);
        } catch(Exception e) {
        }
    }

    /**
     * 调协LSLog输出日志的级别
     * 大于等于此级别的日志将会输出
     * 默认为Log.DEBUG
     *
     * @param logLevel Log.WARN、Log.DEBUG、Log.VERBOSE
     */
    public static void setLogLevel(int logLevel) {
        level = logLevel;
        switch (level) {
            case Log.VERBOSE:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_VERBOSE,LogConstant.LEVEL_VERBOSE);
                break;
            case Log.DEBUG:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_DEBUG,LogConstant.LEVEL_DEBUG);
                break;
            case Log.INFO:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_INFO,LogConstant.LEVEL_INFO);
                break;
            case Log.WARN:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_WARNING,LogConstant.LEVEL_WARNING);
                break;
            case Log.ERROR:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_ERROR,LogConstant.LEVEL_ERROR);
                break;
            case Log.ASSERT:
                LoggerManager.resetLogLevel(LogConstant.LEVEL_ERROR,LogConstant.LEVEL_ERROR);
                break;
        }
    }

    /**
     * @param logMark the logMark to set
     */
    public static void setLogMark(String logMark) {
        LSLog.logMark = logMark;
    }

    public static int e(String tag, String msg) {
        return println(Log.ERROR, tag, msg);
    }

    public static int e(String tag, String msg, Throwable e) {
        return println(Log.ERROR, tag, msg, e);
    }

    public static int w(String tag, String msg) {
        return println(Log.WARN, tag, msg);
    }

    public static int i(String tag, String msg) {
        return println(Log.INFO, tag, msg);
    }

    public static int d(String tag, String msg) {
        return println(Log.DEBUG, tag, msg);
    }

    public static int v(String tag, String msg) {
        return println(Log.VERBOSE, tag, msg);
    }

    /**
     * 是否不打日志
     *
     * @param logLevel
     * @return
     */
    protected static boolean checkNotShowLog(int logLevel) {
        boolean result = true;
        if (logLevel >= level) {
            result = false;
        }
        return result;
    }

    private static boolean isPrintLog = true;
    public static void openLog() {
        isPrintLog=true;
    }

    public static void closeLog() {
        isPrintLog=false;
    }
    protected static int println(int priority, String tag, String msg) {
        if (!isPrintLog) {
            return -1;
        }
        String msgln = String.format("%s [%s %s]", msg, logMark, tag);
        int logResult = 0;
        switch (priority) {
            case Log.VERBOSE:
                logResult = LoggerManager.v(tag,msgln);
                break;
            case Log.DEBUG:
                logResult = LoggerManager.d(tag,msgln);
                break;
            case Log.INFO:
                logResult = LoggerManager.i(tag,msgln);
                break;
            case Log.WARN:
                logResult = LoggerManager.w(tag,msgln);
                break;
            case Log.ERROR:
                logResult = LoggerManager.e(tag,msgln);
                break;
            case Log.ASSERT:
                logResult = LoggerManager.e(tag,msgln);
                break;

        }
        return logResult;
    }

    protected static int println(int priority, String tag, String msg, Throwable tr) {
        msg = String.format("%s\r\nstackTrace:%s", msg, Log.getStackTraceString(tr));

        return println(priority, tag, msg);
    }

    /**
     * 打印毫秒
     * tag会在debug日志的tag中显示
     * 可以与后面的logMillionSecondStep,logMillionSecondEnd匹配使用
     *
     * @param tag
     */
    public static void logMillionSecondStart(String tag) {
        if (checkNotShowLog(Log.DEBUG))
            return;
        Long startTimeLong = System.currentTimeMillis();
        millionSeconndStartMap.put(tag, startTimeLong);
        d(logMark, String.format("mslog %s start at %d", tag, startTimeLong));
    }

    /**
     * 打印一个步骤的用时
     * 与logMillionSecondStart配合使用
     *
     * @param tag
     * @param mark
     */
    public static void logMillionSecondStep(String tag, String mark) {
        if (checkNotShowLog(Log.DEBUG))
            return;
        Long currentTimeLong = System.currentTimeMillis();
        Long startTimeLong = millionSeconndStartMap.get(tag);
        Long lastTimeLong = millionSeconndStartMap.get(String.format("%s:last", tag));

        if (startTimeLong == null) {
            startTimeLong = currentTimeLong;
            millionSeconndStartMap.put(tag, startTimeLong);
        }
        if (lastTimeLong == null) {
            lastTimeLong = startTimeLong;
        }

        millionSeconndStartMap.put(String.format("%s:last", tag), currentTimeLong);
        d(logMark, String.format("mslog %s duration %d/%dms at step(%s) since last/first step", tag, currentTimeLong - lastTimeLong, currentTimeLong - startTimeLong, mark));
    }

    /**
     * 结束打印
     * 与logMillionSecondStart\logMillionSecondStep配合使用
     *
     * @param tag
     */
    public static void logMillionSecondEnd(String tag) {
        if (checkNotShowLog(Log.DEBUG))
            return;
        logMillionSecondStep(tag, "end");
        millionSeconndStartMap.remove(tag);
        millionSeconndStartMap.remove(String.format("%s:last", tag));
    }

}
