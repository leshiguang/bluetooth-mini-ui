package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;


import com.lifesense.android.ble.core.application.model.config.Silence;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.SilenceSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 勿扰模式
 *
 * @author alexwu*/
public class NoDisturbModeItem extends SettingItem<Silence> {

    @Override
    public String getTitle() {
        return context.getString(R.string.scale_no_disturb_mode);
    }

    @Override
    public Class getTargetAction() {
        return SilenceSettingActivity.class;
    }


}
