package com.lifesense.android.health.service.deviceconfig.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.ConnectionStateReceiver;
import com.lifesense.android.ble.core.application.model.config.HeartRateSwitch;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.core.application.model.enums.ConnectionState;

import com.lifesense.android.ble.core.application.model.wifi.res.WifiInfo;
import com.lifesense.android.ble.core.serializer.AbstractMeasureData;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

import java.util.ArrayList;
import java.util.List;

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
//            BleDeviceManager.getDefaultManager().scanWifi(s, wifiInfo -> Log.i("LS-BLE", "on scan wifi info :" + wifiInfo.getSsid()));
//            HeartRateSwitch heartRateSwitch = new HeartRateSwitch();
//            heartRateSwitch.setEnable(true);
//
//            BleDeviceManager.getDefaultManager().updateConfig(s, heartRateSwitch, configStatus -> Log.i("LS-BLE", configStatus.name()));
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
        Consumer receiver = abstractMeasureData -> Log.i("Data", JSON.toJSONString(abstractMeasureData));
        BleDeviceManager.getDefaultManager().init(this,"com.leshiguang.saas.rbac.demo.appid",PreferenceStorage.getBondedMac(),receiver);
        BleDeviceManager.getDefaultManager().registerConnectionStatusReceiver(this);
    }

}
