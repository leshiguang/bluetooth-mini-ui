package com.lifesense.miniblesdkdemo;

import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.serializer.AbstractMeasureData;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2021/1/29
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
