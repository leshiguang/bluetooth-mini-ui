package com.lifesense.android.health.service.devicedetails.item.builder;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

/**
 * Create by qwerty
 * Create on 2020/10/27
 **/
public class DeviceNameBuilder extends SettingItem {

    @Override
    public Class getTargetAction() {
        return null;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.scale_device);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TextOnly;
    }

    @Override
    public String getValue() {
        return deviceInfo.getDeviceName();
    }

}
