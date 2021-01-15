package com.lifesense.commonlogic.log;

/**
 * Created by yunfeng on 2017/2/8.
 */

public class FileLogBean {

    private String mTag = null;
    private String mContent = null;
    private long mTime = 0;
    private int mLogLevel = LogConstant.LEVEL_NONE;

    public FileLogBean(int logLevel, String tag, String content) {
        mLogLevel = logLevel;
        mTag = tag;
        mContent = content;
        mTime = System.currentTimeMillis();
    }

    public String getLogLevelStr() {
        String logLevelStr = null;
        switch (mLogLevel) {
            case LogConstant.LEVEL_VERBOSE:
                logLevelStr = LogConstant.LEVEL_VERBOSE_STR;
                break;
            case LogConstant.LEVEL_DEBUG:
                logLevelStr = LogConstant.LEVEL_DEBUG_STR;
                break;
            case LogConstant.LEVEL_INFO:
                logLevelStr = LogConstant.LEVEL_INFO_STR;
                break;
            case LogConstant.LEVEL_WARNING:
                logLevelStr = LogConstant.LEVEL_WARNING_STR;
                break;
            case LogConstant.LEVEL_ERROR:
                logLevelStr = LogConstant.LEVEL_ERROR_STR;
                break;
            case LogConstant.LEVEL_NONE:
                logLevelStr = LogConstant.LEVEL_NONE_NONE;
                break;
            default:
                logLevelStr = LogConstant.LEVEL_NONE_NONE;
                break;
        }
        return logLevelStr;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public int getLogLevel() {
        return mLogLevel;
    }

    public void setLogLevel(int logLevel) {
        this.mLogLevel = logLevel;
    }


}
