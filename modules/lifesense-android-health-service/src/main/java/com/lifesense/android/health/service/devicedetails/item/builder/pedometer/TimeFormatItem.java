package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import com.lifesense.android.ble.core.application.model.config.TimeFormat;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.TimeFormatSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 事件制式
 *
 * @author alexwu*/
public class TimeFormatItem extends SettingItem<TimeFormat> {

    @Override
    public String getTitle() {
        return "时间制式";
    }

    @Override
    public Class getTargetAction() {
        return TimeFormatSettingActivity.class;
    }

}
