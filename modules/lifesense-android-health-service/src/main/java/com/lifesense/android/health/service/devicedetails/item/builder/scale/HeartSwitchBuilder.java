package com.lifesense.android.health.service.devicedetails.item.builder.scale;

import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Stream;
import com.lifesense.android.ble.core.application.BleDeviceManager;

import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.core.serializer.AbstractConfig;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;

import com.lifesense.android.ble.device.band.model.config.HeartRateSwitch;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.devicedetails.model.HeightUnit;
import com.lifesense.android.health.service.devicedetails.model.WeightUnit;
import com.lifesense.android.health.service.devicedetails.item.SettingFactory;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.util.ToastUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2020/10/27
 *
 * @author alexwu*/
public class HeartSwitchBuilder extends SettingItem<HeartRateSwitch> {

    @Override
    public String getTitle() {
        return "心率开关";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.Switch;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        HeartRateSwitch heartRateSwitch = new HeartRateSwitch();
        heartRateSwitch.setEnable(isChecked);
        BleDeviceManager.getDefaultManager().updateConfig(deviceInfo.getMac(), heartRateSwitch, configStatus -> {
            if (configStatus == ConfigStatus.SUCCESS) {
                if (isChecked) {
                    ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_open_heart_rate_msg));
                } else {
                    ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_close_heart_rate_msg));
                }
            } else {
                ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_setting_fail_msg));
            }
        });
    }

    @Override
    public Class getTargetAction() {
        return null;
    }


}
