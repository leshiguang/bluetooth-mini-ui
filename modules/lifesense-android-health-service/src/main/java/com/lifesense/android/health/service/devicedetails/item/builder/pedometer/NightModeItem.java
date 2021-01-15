package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import com.lifesense.android.ble.core.application.model.config.NightMode;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.NightModeSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 夜间模式
 *
 * @author alexwu*/
public class NightModeItem extends SettingItem<NightMode> {


    @Override
    public String getTitle() {
        return context.getString(R.string.scale_night_mode);
    }


    @Override
    public Class getTargetAction() {
        return NightModeSettingActivity.class;
    }
}
