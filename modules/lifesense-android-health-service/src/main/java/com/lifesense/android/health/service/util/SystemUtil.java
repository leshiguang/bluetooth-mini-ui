package com.lifesense.android.health.service.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by lifesense on 16/5/3.
 */
public class SystemUtil {
    public static void openSettingApp(Context context) {
       try{ //存在ActivityNotFoundException问题
           Intent intent = new Intent();
           intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           intent.setData(Uri.fromParts("package", context.getPackageName(), null));
           context.startActivity(intent);
       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    public static boolean isProviderEnabledGps(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            LocationManager myLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (myLocationManager == null) {
                return false;
            }

            try {
                return myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (SecurityException e) {
                // https://report.tingyun.com/mobile/mobileApp/15394/crashDetail?endTime=2017-8-22+10%3A30&timePeriod=43200&crashReportId=0&mobileCrashId=47952603&mobileAppVersionId=140386
                // 加入权限保护
            }
            return false;
        }

        int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF);

        return mode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY ||
                mode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;
    }

    public static void openLocationOpenActivity(Context mContext) {
        try {  //存在ActivityNotFoundException问题
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
