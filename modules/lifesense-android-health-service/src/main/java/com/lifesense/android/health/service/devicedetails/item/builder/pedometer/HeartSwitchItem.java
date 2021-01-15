package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import android.widget.CompoundButton;


import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.config.HeartRateSmartSwitch;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.util.ToastUtil;

import org.apache.commons.collections4.CollectionUtils;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 连续心率监测开关
 *
 * @author alexwu*/
public class HeartSwitchItem extends SettingItem<HeartRateSmartSwitch> {


    @Override
    public String getTitle() {
        return context.getString(R.string.scale_heart_rate);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.Switch;
    }

    @Override
    public boolean isOpenSwitch() {
        if (CollectionUtils.isEmpty(configs)) {
            return false;
        }
        return configs.get(0).getMode() != HeartRateSmartSwitch.Mode.CLOSE;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if ((configs.get(0).getMode() == HeartRateSmartSwitch.Mode.CLOSE ? false : true) == isChecked) {
            return;
        }
        HeartRateSmartSwitch heartRateSwitch = new HeartRateSmartSwitch();
        heartRateSwitch.setMode(isChecked ? HeartRateSmartSwitch.Mode.ENABLE : HeartRateSmartSwitch.Mode.CLOSE);
        BleDeviceManager.getDefaultManager().updateConfig(deviceInfo.getMac(), heartRateSwitch, new Consumer<ConfigStatus>() {
            @Override
            public void accept(ConfigStatus configStatus) throws Exception {
                if (configStatus == ConfigStatus.SUCCESS) {
                    if (isChecked) {
                        ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_open_heart_rate_msg));
                    } else {
                        ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_close_heart_rate_msg));
                    }
                } else {
                    ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_setting_fail_msg));
                }
            }
        });
    }

    @Override
    public Class getTargetAction() {
        return null;
    }


}
