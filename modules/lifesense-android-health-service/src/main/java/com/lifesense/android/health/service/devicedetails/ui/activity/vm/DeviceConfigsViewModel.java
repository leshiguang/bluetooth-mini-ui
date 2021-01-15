package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.health.service.common.ui.BaseViewModel;
import com.lifesense.android.health.service.devicedetails.item.SettingFactory;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.DeviceConfigsRvAdapter;

/**
 * Create by qwerty
 * Create on 2021/1/7
 **/
public class DeviceConfigsViewModel extends BaseViewModel {
    public static final String EXTRA_MAC = "extra_mac";
    private MutableLiveData<DeviceConfigsRvAdapter> adapter = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity activity) {
        String mac = activity.getIntent().getStringExtra(EXTRA_MAC);
        DeviceConfigsRvAdapter adapter = new DeviceConfigsRvAdapter();
        adapter.setItems(SettingFactory.create(activity, BleDeviceManager.getDefaultManager().getDeviceInfoWithMac(mac)));
        this.adapter.setValue(adapter);
    }

    public MutableLiveData<DeviceConfigsRvAdapter> getAdapter() {
        return adapter;
    }
}
