package com.lifesense.android.health.service.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Optional;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PreferenceStorage {
    private static final String TAG = PreferenceStorage.class.getSimpleName();
    private static Context context;
    public static String PREFERENCE_NAME = "TrineaAndroidCommon";
    public static void init(Context context) {
        PreferenceStorage.context = context.getApplicationContext();
    }

    public static List<String> getBondedMac() {
        String macs = getString("bonded_devices");
        return (List)(TextUtils.isEmpty(macs) ? Collections.emptyList() : new ArrayList<>(Arrays.asList(macs.split(","))));
    }

    public static void addBondDevice(String mac) {
        String macs = getString("bonded_devices");
        if (TextUtils.isEmpty(macs)) {
            putString("bonded_devices", mac);
        } else if (!macs.contains(mac)) {
            putString("bonded_devices", macs.concat(",").concat(mac));
        }

    }


    public static void removeBondDevice(String mac) {
        List<String> bondedMac = getBondedMac();
        if (!bondedMac.isEmpty()) {
            bondedMac.remove(mac);
            putString("bonded_devices", TextUtils.join(",", bondedMac));
        }
    }


    public static String getString(String key) {
        return getString(key, (String)null);
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getString(key, defaultValue);
    }

    public static boolean putString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public static void cacheDeviceInfo(String mac, DeviceInfo deviceInfo) {
        putString(mac, JSONObject.toJSONString(deviceInfo));
        deviceInfo.setReaded(true);
    }


    public static DeviceInfo fillDeviceInfoFromCache(String mac, DeviceInfo deviceInfo) {
        String cacheDeviceInfo = getString(mac, null);
        if (TextUtils.isEmpty(cacheDeviceInfo)) {
            return null;
        }
        DeviceInfo cachedDeviceInfo = JSON.parseObject(cacheDeviceInfo, DeviceInfo.class);
        try {
            deviceInfo.setDeviceType(cachedDeviceInfo.getDeviceType());
            deviceInfo.setMac(cachedDeviceInfo.getMac());
            deviceInfo.setDeviceId(cachedDeviceInfo.getDeviceId());
            deviceInfo.setDeviceName(cachedDeviceInfo.getDeviceName());
            deviceInfo.setModelNumber(cachedDeviceInfo.getModelNumber());
            deviceInfo.setDeviceType(cachedDeviceInfo.getDeviceType());
            deviceInfo.setSoftwareVersion(cachedDeviceInfo.getSoftwareVersion());
            deviceInfo.setHardwareVersion(cachedDeviceInfo.getHardwareVersion());
            deviceInfo.setManufactureName(cachedDeviceInfo.getManufactureName());
            deviceInfo.setProtocolType(cachedDeviceInfo.getProtocolType());
            deviceInfo.setManufactureId(cachedDeviceInfo.getManufactureId());
            deviceInfo.setReaded(true);
        } catch (Exception e) {
            Log.i(TAG,Optional.of(e.getMessage()).orElse("NPE"));
        }
        return deviceInfo;
    }

    public static List<DeviceInfo> getBondedDeviceInfo() {
        List<String> bondedMacs = getBondedMac();
        List<DeviceInfo> bondedDeviceInfos = new ArrayList<>();
        if(bondedMacs != null) {
            for (String mac : bondedMacs) {
                DeviceInfo deviceInfo = new DeviceInfo();
                fillDeviceInfoFromCache(mac,deviceInfo);
                bondedDeviceInfos.add(deviceInfo);
            }
        }
        return bondedDeviceInfos;
    }

    public static DeviceInfo getBondedDeviceInfoByMac(String mac) {
        DeviceInfo deviceInfo = new DeviceInfo();
        fillDeviceInfoFromCache(mac,deviceInfo);
        return deviceInfo;
    }


}
