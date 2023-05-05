package com.lifesense.android.health.service.deviceconfig.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.MainThread;

import com.alibaba.fastjson.JSON;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.ConnectionStateReceiver;

import com.lifesense.android.ble.core.application.model.enums.ConnectionState;


import com.lifesense.android.ble.core.serializer.AbstractMeasureData;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.ble.device.fatscale.model.WeightMeasureData;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.MeasurementDataActivity;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    @SuppressLint("CheckResult")
    @Override
    public void onConnectionStateChange(String s, ConnectionState connectionState) {
        Log.i("onConnectionStateChange", connectionState.name());
        if (connectionState == ConnectionState.CONNECTED) {
            DeviceInfo deviceInfoWithMac = BleDeviceManager.getDefaultManager().getDeviceInfoWithMac(s);
            Log.i("LS-Bluetooth", JSON.toJSONString(deviceInfoWithMac));


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
        BleDeviceManager.getDefaultManager().setDebug(true);
        BleDeviceManager.getDefaultManager().init(this,"lx62a113f084ef6a98","12345", PreferenceStorage.getBondedMac(), receiver);

        BleDeviceManager.getDefaultManager().registerConnectionStatusReceiver(this);
    }

    @MainThread
    public void show( AbstractMeasureData abstractMeasureData) {
        if (abstractMeasureData instanceof WeightMeasureData) {
            if (((WeightMeasureData) abstractMeasureData).isProcessingData()) {
                Log.i("LS-Bluetooth", "收到测量过程数据");
                return;
            }
        }
        abstractMeasureData.setDeviceInfo(null);
        Log.i("Data", JSON.toJSONString(abstractMeasureData));
//        Intent intent = new Intent(ApplicationContext.context, MeasurementDataActivity.class);
//        intent.putExtra("measurementData", JSON.toJSONString(abstractMeasureData));
//        startActivity(intent);
    }
}
