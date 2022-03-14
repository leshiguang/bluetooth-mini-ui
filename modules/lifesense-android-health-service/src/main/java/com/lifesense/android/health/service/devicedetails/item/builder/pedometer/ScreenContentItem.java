package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;


import com.lifesense.android.ble.device.band.model.config.Page;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.ScreenContentSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 屏幕内容
 **/
public class ScreenContentItem extends SettingItem<Page> {

    @Override
    public String getTitle() {
        return "屏幕内容";
    }


    @Override
    public Class getTargetAction() {
        return ScreenContentSettingActivity.class;
    }


}
