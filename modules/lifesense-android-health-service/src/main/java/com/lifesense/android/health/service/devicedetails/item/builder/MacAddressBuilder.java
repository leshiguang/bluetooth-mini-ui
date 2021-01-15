package com.lifesense.android.health.service.devicedetails.item.builder;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

/**
 * Create by qwerty
 * Create on 2020/10/27
 **/
public class MacAddressBuilder extends SettingItem {

    @Override
    public Class getTargetAction() {
        return null;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.scale_mac_address);
    }

    @Override
    public String getValue() {
        return deviceInfo.getMac();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TextOnly;
    }

}
