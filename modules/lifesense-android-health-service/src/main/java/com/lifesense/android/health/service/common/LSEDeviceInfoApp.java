package com.lifesense.android.health.service.common;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.lifesense.android.ble.core.valueobject.DeviceInfo;

import java.io.Serializable;


/**
 * Created by lexin on 17/3/20.
 */

public class LSEDeviceInfoApp implements Comparable, Serializable {

    private DeviceInfo mLSEDeviceInfo;

    private String deviceId;
    private String deviceName;
    private String macAddress;
    private int rssi;

    public DeviceInfo getLSEDeviceInfo() {
        return mLSEDeviceInfo;
    }

    public void setLSEDeviceInfo(DeviceInfo LSEDeviceInfo) {
        mLSEDeviceInfo = LSEDeviceInfo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getDeviceName() {
        if (!TextUtils.isEmpty(macAddress)) {
            String replacedMac = macAddress.replace(":", "");
            return deviceName + replacedMac.substring(replacedMac.length() - 4);
        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || macAddress == null) {
            return false;
        }

        if (o instanceof LSEDeviceInfoApp) {
            LSEDeviceInfoApp app = (LSEDeviceInfoApp) o;
            return macAddress.equalsIgnoreCase(app.getMacAddress());
        }

        return false;
    }

    @Override
    public int compareTo(Object o) {
        LSEDeviceInfoApp lse = (LSEDeviceInfoApp) o;
        if (lse.getRssi() > rssi) {
            return 1;
        } else {
            return -1;
        }
    }

    public LSEDeviceInfoApp() {
    }

    protected LSEDeviceInfoApp(Parcel in) {
        this.mLSEDeviceInfo = in.readParcelable(DeviceInfo.class.getClassLoader());
        this.deviceId = in.readString();
        this.deviceName = in.readString();
        this.macAddress = in.readString();
        this.rssi = in.readInt();
    }

    @Override
    public String toString() {
        return "LSEDeviceInfoApp{" +
                "mLSEDeviceInfo=" + mLSEDeviceInfo +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", rssi=" + rssi +
                '}';
    }
}
