package com.lifesense.android.health.service.devicedetails.item.builder.scale;


import com.lifesense.android.ble.device.band.model.config.LengthUnitConfig;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.model.HeightUnit;
import com.lifesense.android.health.service.devicedetails.ui.activity.DeviceUnitSettingActivity;

import org.apache.commons.collections4.CollectionUtils;

public class LengthUnitSettingBuilder extends SettingItem<LengthUnitConfig> {

    @Override
    public String getTitle() {
        return "长度单位";
    }

    @Override
    public String getValue() {
        if (CollectionUtils.isEmpty(configs)) {
            return HeightUnit.UNIT_CM.getUnitName(context);
        }
        return HeightUnit.getUnitByNetUnitType(configs.get(0).getUnitType().getCommand()).getUnitName(context);
    }

    @Override
    public Class getTargetAction() {
        return DeviceUnitSettingActivity.class;
    }


}
