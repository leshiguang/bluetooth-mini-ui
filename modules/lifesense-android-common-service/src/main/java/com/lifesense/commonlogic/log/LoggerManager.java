package com.lifesense.commonlogic.log;

import android.content.Context;
import android.util.Log;

/**
 * Created by yunfeng on 2017/2/8.
 */

public class LoggerManager {

    private static LogInitInfo sInitInfo = null;
    private static int sSysLogLevel = LogConstant.LEVEL_NONE;
    private static int sFileLogLevel = LogConstant.LEVEL_NONE;
    private static FileLogWorkThread sFileLogWorkThread = null;

    public static LogInitInfo createDefaultInitInfo(Context context) {
        if(context == null) {
            return null;
        }
        String dirFile = context.getExternalFilesDir(null) + "/log/";
        String fileName = "lsys_android";
        int sysLogLevel = LogConstant.LEVEL_NONE;
        int fileLogLevel = LogConstant.LEVEL_ERROR;
        LogInitInfo initInfo = new LogInitInfo(context,dirFile,fileName,sysLogLevel,fileLogLevel);
        return initInfo;
    }

    public static void initLogInfo(LogInitInfo initInfo) {
        sInitInfo = initInfo;
        if(sInitInfo == null) {
            return;
        }
        sSysLogLevel = sInitInfo.getSysLogLevel();
        sFileLogLevel = sInitInfo.getFileLogLevel();
        sFileLogWorkThread = new FileLogWorkThread(sInitInfo);
    }

    public static void resetLogLevel(int sysLogLevel, int fileLogLevel) {
        sSysLogLevel = sysLogLevel;
        sFileLogLevel = fileLogLevel;
        if(sInitInfo != null) {
            sInitInfo.setSysLogLevel(sysLogLevel);
            sInitInfo.setFileLogLevel(fileLogLevel);
        }
    }

    public static int v(String tag, String msg) {
        int logLevel = LogConstant.LEVEL_VERBOSE;
        int logResult = 0;
        if(logLevel >= sSysLogLevel) {
            logResult = Log.v(tag,msg);
        }

        if(logLevel >= sFileLogLevel) {
            addFileLogBean(logLevel,tag,msg);
        }
        return logResult;
    }

    public static int d(String tag, String msg) {
        int logLevel = LogConstant.LEVEL_DEBUG;
        int logResult = 0;
        if(logLevel >= sSysLogLevel) {
            logResult = Log.d(tag,msg);
        }

        if(logLevel >= sFileLogLevel) {
            addFileLogBean(logLevel,tag,msg);
        }
        return logResult;
    }

    public static int i(String tag, String msg) {
        int logLevel = LogConstant.LEVEL_INFO;
        int logResult = 0;
        if(logLevel >= sSysLogLevel) {
            logResult = Log.i(tag,msg);
        }

        if(logLevel >= sFileLogLevel) {
            addFileLogBean(logLevel,tag,msg);
        }
        return logResult;
    }

    public static int w(String tag, String msg) {
        int logLevel = LogConstant.LEVEL_WARNING;
        int logResult = 0;
        if(logLevel >= sSysLogLevel) {
            logResult = Log.w(tag,msg);
        }

        if(logLevel >= sFileLogLevel) {
            addFileLogBean(logLevel,tag,msg);
        }
        return logResult;
    }

    public static int e(String tag, String msg) {
        int logLevel = LogConstant.LEVEL_ERROR;
        int logResult = 0;
        if(logLevel >= sSysLogLevel) {
            logResult = Log.e(tag,msg);
        }

        if(logLevel >= sFileLogLevel) {
            addFileLogBean(logLevel,tag,msg);
        }
        return logResult;
    }

    public static void zipLogFile(ZipLogAction action) {
        if(action == null || action.getListener() == null) {
            return;
        }
        if(sFileLogWorkThread != null) {
            sFileLogWorkThread.setZipFileAction(action);
        } else {
            action.getListener().onResult(false,null);
        }
    }

    private static void addFileLogBean(int logLevel, String tag, String msg) {
        if(sFileLogWorkThread != null) {
            FileLogBean logBean = new FileLogBean(logLevel,tag,msg);
            sFileLogWorkThread.addLogBean(logBean);
        }
    }


}
