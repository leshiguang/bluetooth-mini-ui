package com.lifesense.utils;

import android.content.Context;

import com.lifesense.utils.file.FileUtil;

/**
 * Created by rolandxu on 16/6/28.
 */
public class FileUtilCommon extends FileUtil {

    protected static String DISK_CACHE_PATH = null;

    static {
        DISK_CACHE_PATH = "lzhealth" + "/";
    }

    /**
     * app文件夹路径
     *
     * @return
     */
    public static String getAppFolderPath(Context context) {
        String cachePath;
        // /mnt/sdcard判断有无SD卡
        if (isSDCardExist()) {
            cachePath = String.format(FOLDER_MERGE_FORMATER, getSDCardDir(), DISK_CACHE_PATH);
        } else {
            // 没有就创建到手机内存
            cachePath = String.format(FOLDER_MERGE_FORMATER, context.getCacheDir(), DISK_CACHE_PATH);
        }
        ensureDir(cachePath);
        return cachePath;
    }
    /**
     * 获取日志路径
     *
     * @return
     */
    public static String getLogPath(Context context) {
        return getAppFolderPath(context) + "/log";
    }
}
