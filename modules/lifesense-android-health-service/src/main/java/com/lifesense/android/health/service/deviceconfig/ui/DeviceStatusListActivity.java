package com.lifesense.android.health.service.deviceconfig.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;

import com.alibaba.fastjson.JSON;
import com.lifesense.android.ble.core.application.ApplicationContext;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.ConnectionStateReceiver;
import com.lifesense.android.ble.core.application.model.BatteryInfo;
import com.lifesense.android.ble.core.application.model.config.HeartRateSmartSwitch;
import com.lifesense.android.ble.core.application.model.config.HeartRateSwitch;
import com.lifesense.android.ble.core.application.model.config.Language;
import com.lifesense.android.ble.core.application.model.config.LengthUnitConfig;
import com.lifesense.android.ble.core.application.model.config.TimeFormat;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.core.application.model.enums.ConnectionState;


import com.lifesense.android.ble.core.serializer.AbstractMeasureData;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.MeasurementDataActivity;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.prefs.PreferenceStorage;
import com.lifesense.android.health.service.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:  winds
 * Email:   heardown@163.com
 * Date:    2019/9/12.
 * Desc:    设备状态列表页
 */
public class DeviceStatusListActivity extends BaseDataBindingActivity<DeviceStatusListViewModel> implements ConnectionStateReceiver {

    public static Intent getIntent(Context context) {
        return new Intent(context, DeviceStatusListActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_device_status_list;
    }

    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void preInitView() {
        super.preInitView();
        PreferenceStorage.init(this);
        initBleSDK();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.packingDeviceState(this,ConnectionState.CONNECTED);
    }
    @Override
    public void onConnectionStateChange(String s, ConnectionState connectionState) {
        Log.i("onConnectionStateChange", connectionState.name());
        if (connectionState == ConnectionState.CONNECTED) {



        }

        viewModel.packingDeviceState(this, connectionState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleDeviceManager.getDefaultManager().unRegisterConnectionStatusReceiver(this);
    }


    private void initBleSDK() {
        //静默登录
        Consumer receiver = abstractMeasureData -> show((AbstractMeasureData) abstractMeasureData);
        BleDeviceManager.getDefaultManager().init(this,"com.leshiguang.saas.rbac.demo.appid", PreferenceStorage.getBondedMac(), receiver);
        BleDeviceManager.getDefaultManager().registerConnectionStatusReceiver(this);
    }

    @MainThread
    public void show( AbstractMeasureData abstractMeasureData) {
        Log.i("Data", JSON.toJSONString(abstractMeasureData));
        Intent intent = new Intent(ApplicationContext.context, MeasurementDataActivity.class);
        intent.putExtra("measurementData", JSON.toJSONString(abstractMeasureData));
        startActivity(intent);
    }
}
