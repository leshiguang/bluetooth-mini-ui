package com.lifesense.android.health.service.util;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by lee on 2016/3/10.
 */
public class WindowUtils {

    private WindowUtils() {
    }

    /**
     * 保持屏幕长亮
     *
     * @param activity
     */
    public static void keepScreenOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 取消屏幕长亮
     *
     * @param activity
     */
    public static void cancelScreenOn(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
