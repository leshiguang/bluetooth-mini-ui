package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;


import com.lifesense.android.ble.device.band.model.config.DialPlate;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.DialPeaceSettingActivity;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

/**
 * Create by qwerty
 * Create on 2020/10/28
 * 表盘样式
 *
 * @author alexwu*/
public class DialPlateItem extends SettingItem<DialPlate> {

    @Override
    public String getTitle() {
        return context.getString(R.string.scale_dial_peace);
    }


    @Override
    public Class getTargetAction() {
        return DialPeaceSettingActivity.class;
    }


}
