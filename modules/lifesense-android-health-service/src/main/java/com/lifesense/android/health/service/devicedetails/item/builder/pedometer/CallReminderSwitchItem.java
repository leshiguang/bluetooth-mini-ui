package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import android.widget.CompoundButton;


import com.lifesense.android.ble.core.application.BleDeviceManager;

import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;


import com.lifesense.android.ble.device.band.model.config.Call;
import com.lifesense.android.ble.device.band.model.enums.VibrationMode;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.util.ToastUtil;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Create by qwerty
 * Create on 2020/10/28
 * 来电提醒开关
 *
 * @author alexwu*/
public class CallReminderSwitchItem extends SettingItem<Call> {


    @Override
    public String getTitle() {
        return "来电提醒";
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
        return configs.get(0).isEnable();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (configs.get(0).isEnable() == isChecked) {
            return;
        }
        Call call = new Call();
        call.setEnable(isChecked);
        call.setReminderType(Call.ReminderType.CALL);
        call.setVibrationMode(VibrationMode.CONTINUOUS_VIBRATION);
        call.setVibrationLevel(9);
        call.setVibrationLevel1(9);
        call.setVibrationTime((short) 60);
        BleDeviceManager.getDefaultManager().updateConfig(deviceInfo.getMac(), call, configStatus -> {
            if (configStatus == ConfigStatus.SUCCESS) {
                ToastUtil.showCenterShowToast(context, "来电提醒已" + (isChecked ? "打开" : "关闭"));
            } else {
                ToastUtil.showCenterShowToast(context, "设置失败");
            }
        });
    }

    @Override
    public Class getTargetAction() {
        return null;
    }

}
