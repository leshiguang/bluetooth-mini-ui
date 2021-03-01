package com.lifesense.android.health.service.deviceconfig.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.enums.ConnectionState;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.common.ui.BaseViewModel;
import com.lifesense.android.health.service.device.DeviceStateWrapper;
import com.lifesense.android.health.service.devicebind.ui.activity.DeviceConnectSearchActivity;
import com.lifesense.android.health.service.deviceconfig.adapter.DeviceStatusListAdapter;
import com.lifesense.android.health.service.prefs.PreferenceStorage;
import com.lifesense.utils.PreferencesUtils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2021/1/7
 **/
public class DeviceStatusListViewModel extends BaseViewModel {
    private MutableLiveData<DeviceStatusListAdapter> adapter = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity activity) {
        DeviceStatusListAdapter adapter = new DeviceStatusListAdapter();
        this.adapter.setValue(adapter);
    }

    public MutableLiveData<DeviceStatusListAdapter> getAdapter() {
        return adapter;
    }

    public void onClick(View v) {
        v.getContext().startActivity(DeviceConnectSearchActivity.makeIntent(v.getContext()));
    }

    /**
     * 处理已绑定设备信息
     */
    public void packingDeviceState(Context context) {
        List<DeviceStateWrapper> list = new ArrayList<>();
        String userId = PreferencesUtils.getString(context, "userId", "0");
        if (!TextUtils.isEmpty(userId)) {

            List<DeviceInfo> devices = PreferenceStorage.getBondedDeviceInfo();
            if (CollectionUtils.isNotEmpty(devices)) {
                Iterator<DeviceInfo> iterator = devices.iterator();
                while (iterator.hasNext()) {
                    DeviceInfo device = iterator.next();
                    ConnectionState state = BleDeviceManager.getDefaultManager().getDeviceConnectState(device.getMac());
                    DeviceStateWrapper stateWrapper = new DeviceStateWrapper(device, state);
                    list.add(stateWrapper);
                }
            }

        }

        adapter.getValue().setItems(list);
        adapter.setValue(adapter.getValue());
    }


}
