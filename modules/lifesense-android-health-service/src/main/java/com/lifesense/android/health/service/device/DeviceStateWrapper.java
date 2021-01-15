package com.lifesense.android.health.service.device;

import androidx.databinding.BaseObservable;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.enums.ConnectionState;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;

/**
 * Author:  winds
 * Email:   heardown@163.com
 * Date:    2019/9/18.
 * Desc:
 */
public class DeviceStateWrapper extends BaseObservable {
    protected DeviceInfo device;
    protected int batteryState;   //电池状态
    protected int battery; //电池电量信息

    public DeviceStateWrapper() {

    }

    public DeviceStateWrapper(DeviceInfo device, ConnectionState state) {
        this.device = device;
        this.state = state;
    }

    public String getDeviceId() {
        if (device != null) {
            return device.getMac();
        }
        return null;
    }

    private ConnectionState state;

    public DeviceInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public int getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(int batteryState) {
        this.batteryState = batteryState;
    }

    public ConnectionState getState() {
        return state;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }

    public boolean isConnected() {
        return state != null && state == ConnectionState.CONNECTED;
    }

    public boolean checkDeviceConnectState() {
        if (device != null) {
            this.state = BleDeviceManager.getDefaultManager().getDeviceConnectState(device.getMac());
        }
        return isConnected();
    }

    @Override
    public DeviceStateWrapper clone() throws CloneNotSupportedException {
        return (DeviceStateWrapper) super.clone();
    }
}

