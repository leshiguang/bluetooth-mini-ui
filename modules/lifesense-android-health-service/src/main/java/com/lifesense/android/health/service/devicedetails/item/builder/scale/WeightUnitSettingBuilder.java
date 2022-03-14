package com.lifesense.android.health.service.devicedetails.item.builder.scale;


import com.lifesense.android.ble.device.fatscale.model.config.WeightUnitConfig;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.model.WeightUnit;
import com.lifesense.android.health.service.devicedetails.ui.activity.DeviceUnitSettingActivity;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

import org.apache.commons.collections4.CollectionUtils;


/**
 * Create by qwerty
 * Create on 2020/10/27
 **/
public class WeightUnitSettingBuilder extends SettingItem<WeightUnitConfig> {

    private static final int UNIT_SETTING_REQ = 1001;


    @Override
    public String getTitle() {
        return context.getString(R.string.scale_unit);
    }

    @Override
    public String getValue() {
        if (CollectionUtils.isEmpty(configs)) {
            return WeightUnit.UNIT_KG.getUnitName(context);
        }
        return WeightUnit.getUnitByNetUnitType(configs.get(0).getUnitType().getCommand()).getUnitName(context);
    }

    @Override
    public Class getTargetAction() {
        return DeviceUnitSettingActivity.class;
    }

}
