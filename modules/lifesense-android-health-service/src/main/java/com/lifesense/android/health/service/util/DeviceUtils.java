package com.lifesense.android.health.service.util;

import android.content.Context;
import android.content.Intent;

import com.lifesense.android.health.service.devicedetails.ui.activity.DeviceConfigsActivity;


public class DeviceUtils {






    public static Intent getDeviceSettingsIntent(Context context, String mac) {
        return DeviceConfigsActivity.makeIntent(context, mac);
    }

}
