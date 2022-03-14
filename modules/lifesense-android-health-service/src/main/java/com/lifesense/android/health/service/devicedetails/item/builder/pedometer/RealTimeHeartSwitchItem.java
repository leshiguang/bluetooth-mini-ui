package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import android.widget.CompoundButton;

import com.lifesense.android.ble.core.application.BleDeviceManager;

import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.device.band.model.config.RealTimeHeartRateSwitch;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.devicedetails.repository.ConfigsRepository;
import com.lifesense.android.health.service.util.ToastUtil;

import org.apache.commons.collections4.CollectionUtils;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 连续心率监测开关
 *
 * @author alexwu*/
public class RealTimeHeartSwitchItem extends SettingItem<RealTimeHeartRateSwitch> {


    @Override
    public String getTitle() {
        return context.getString(R.string.scale_realtime_heart_rate);
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
        if ((configs.get(0).isEnable()) == isChecked) {
            return;
        }
        RealTimeHeartRateSwitch heartRateSwitch = new RealTimeHeartRateSwitch();
        heartRateSwitch.setEnable(isChecked);
        BleDeviceManager.getDefaultManager().updateConfig(deviceInfo.getMac(), heartRateSwitch, new Consumer<ConfigStatus>() {
            @Override
            public void accept(ConfigStatus configStatus) throws Exception {
                if (configStatus == ConfigStatus.SUCCESS) {
                    ConfigsRepository.saveConfig(deviceInfo.getMac(), heartRateSwitch);
                    if (isChecked) {
                        ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_open_heart_rate_msg));
                    } else {
                        ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_close_heart_rate_msg));
                    }
                } else {
                    ToastUtil.showCustomCenterShowToast(context,context.getString(R.string.scale_setting_fail_msg));
                }
                fetchItemConfigs();
            }
        });
    }

    @Override
    public Class getTargetAction() {
        return null;
    }


}
