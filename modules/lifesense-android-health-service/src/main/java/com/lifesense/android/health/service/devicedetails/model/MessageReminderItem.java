package com.lifesense.android.health.service.devicedetails.model;

import android.widget.CompoundButton;

import com.lifesense.android.ble.device.band.model.config.Call;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;

/**
 * Create by qwerty
 * Create on 2020/12/31
 **/
public class MessageReminderItem extends SettingItem<Call> {
    private Call call;
    @Override
    public ItemType getItemType() {
        return ItemType.Switch;
    }

    @Override
    public Class getTargetAction() {
        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        super.onCheckedChanged(buttonView, isChecked);
    }
}
