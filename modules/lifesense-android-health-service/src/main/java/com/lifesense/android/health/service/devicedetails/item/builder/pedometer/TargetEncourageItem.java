package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import com.lifesense.android.ble.device.band.model.config.TargetEncourage;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.EncourageSingleSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/28
 * 目标设置
 *
 * @author alexwu*/
public class TargetEncourageItem extends SettingItem<TargetEncourage> {


    @Override
    public String getTitle() {
        return "目标设置";
    }



    @Override
    public Class getTargetAction() {
        return EncourageSingleSettingActivity.class;
    }


}
