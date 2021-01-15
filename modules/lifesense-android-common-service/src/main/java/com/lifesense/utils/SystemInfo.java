package com.lifesense.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings.Secure;

/**
 * Android系统信息
 *
 * @author rolandxu
 */
public class SystemInfo {
    private static final String TAG = "NameNotFoundException";

    /**
     * 版本号
     *
     * @param app
     * @return
     */
    public static int getAppVersionCode(Context app) {
        int result = 0;
        PackageInfo packInfo = getPackageInfo(app);
        if (packInfo != null) {
            result = packInfo.versionCode;
        }
        return result;
    }

    /**
     * 获取应用程序简易版本名，去掉括号里面的build，示例 2.5
     *
     * @return
     */
    public static String getAppVersionName(Context app) {
        String appVersionName = getAppVersionFullName(app);
        if (appVersionName.contains("(")) {
            appVersionName = appVersionName.split("\\(")[0];
        }

        return appVersionName;
    }

    /**
     * 应用名称
     *
     * @param app
     * @return
     */
    public static String getAppVersionFullName(Context app) {
        String appVersionFullName = getPackageInfo(app).versionName;
        return appVersionFullName;
    }


    protected static PackageInfo getPackageInfo(Context app) {
        if (app == null) return null;
        return getPackageInfo(app, app.getPackageName());
    }


    protected static PackageInfo getPackageInfo(Context app, String packageName) {
        if (app == null) return null;
        PackageManager packageManager = app.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            LSLog.w(TAG, "getPackageInfo NameNotFoundException");
        }
        return packInfo;
    }

    /**
     * 系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    //ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置.传闻2.2上经常失效
    public static String getAndroid_ID(Context app) {
        if (app == null) return null;
        return Secure.getString(app.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context app, float dpValue) {
        if (app == null)
            return 0;
        final float scale = getDeviceDisplayDensity(app);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 屏幕密度
     *
     * @return
     */
    public static float getDeviceDisplayDensity(Context app) {
        if (app == null)
            return 0;
        return app.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }



}
