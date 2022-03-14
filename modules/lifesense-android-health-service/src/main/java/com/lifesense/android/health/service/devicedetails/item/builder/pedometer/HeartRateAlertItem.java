package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;



import com.lifesense.android.ble.device.band.model.config.HeartRateAlert;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.HeartRateAlertSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/28
 * 心率预警
 *
 * @author alexwu*/
public class HeartRateAlertItem extends SettingItem<HeartRateAlert> {

    @Override
    public String getTitle() {
        return "心率预警";
    }


    @Override
    public Class getTargetAction() {
        return HeartRateAlertSettingActivity.class;
    }


}
