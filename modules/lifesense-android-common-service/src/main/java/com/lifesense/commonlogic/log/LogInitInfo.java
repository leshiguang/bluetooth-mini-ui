package com.lifesense.commonlogic.log;

import android.content.Context;

/**
 * Created by yunfeng on 2017/2/8.
 */

public class LogInitInfo {

    private Context mContext = null;
    private String  mFileDir = null;
    private String  mFileName = null;
    private int     mSysLogLevel = LogConstant.LEVEL_NONE;
    private int     mFileLogLevel = LogConstant.LEVEL_NONE;

    public LogInitInfo(Context context, String fileDir, String fileName, int sysLogLevel, int fileLogLevel) {
        mContext = context;
        mFileDir = fileDir;
        mFileName = fileName;
        mSysLogLevel = sysLogLevel;
        mFileLogLevel = fileLogLevel;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getFileDir() {
        return mFileDir;
    }

    public void setFileDir(String fileDir) {
        this.mFileDir = fileDir;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public int getSysLogLevel() {
        return mSysLogLevel;
    }

    public void setSysLogLevel(int sysLogLevel) {
        this.mSysLogLevel = sysLogLevel;
    }

    public int getFileLogLevel() {
        return mFileLogLevel;
    }

    public void setFileLogLevel(int fileLogLevel) {
        this.mFileLogLevel = fileLogLevel;
    }



}
