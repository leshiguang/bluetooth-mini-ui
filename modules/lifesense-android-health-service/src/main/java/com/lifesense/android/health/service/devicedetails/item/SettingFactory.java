package com.lifesense.android.health.service.devicedetails.item;

import androidx.appcompat.app.AppCompatActivity;


import com.lifesense.android.ble.core.application.model.enums.DeviceType;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.devicedetails.item.builder.UnBindBuilder;
import com.lifesense.android.health.service.devicedetails.item.builder.DeviceNameBuilder;
import com.lifesense.android.health.service.devicedetails.item.builder.FirmwareUpgradeBuilder;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.TargetEncourageItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.EventReminderItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.HeartRateAlertItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.NightModeItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.NoDisturbModeItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.ScreenContentItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.TimeFormatItem;
import com.lifesense.android.health.service.devicedetails.item.builder.MacAddressBuilder;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.MessageReminderItem;
import com.lifesense.android.health.service.devicedetails.item.builder.pedometer.HeartSwitchItem;
import com.lifesense.android.health.service.devicedetails.item.builder.scale.WeightUnitSettingBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create by qwerty
 * Create on 2020/10/12
 **/
public class SettingFactory {
    private static Map<DeviceType,List<Class<? extends SettingItem>>> builderMaps = new HashMap<>();

    static {
        builderMaps.put(DeviceType.FatScale,new LinkedList<Class<? extends SettingItem>>(){
            {
                add(DeviceNameBuilder.class);
                add(WeightUnitSettingBuilder.class);
                add(MacAddressBuilder.class);
                add(FirmwareUpgradeBuilder.class);
                add(UnBindBuilder.class);
            }
        });

        builderMaps.put(DeviceType.Bracelet,new LinkedList<Class<? extends SettingItem>>() {
            {
                add(DeviceNameBuilder.class);
                add(MacAddressBuilder.class);
//                add(CallReminderSwitchItem.class);
//                add(DialPlateItem.class);
                add(TargetEncourageItem.class);
                add(EventReminderItem.class);
                add(HeartRateAlertItem.class);
                add(HeartSwitchItem.class);
                add(MessageReminderItem.class);
                add(NightModeItem.class);
                add(NoDisturbModeItem.class);
//                add(ScreenDirectionItem.class);
                add(ScreenContentItem.class);
                add(TimeFormatItem.class);
//                add(WearingItem.class);
                add(FirmwareUpgradeBuilder.class);
                add(UnBindBuilder.class);
            }
        });
        builderMaps.put(DeviceType.BloodPressure,new LinkedList<Class<? extends SettingItem>>() {
            {
                add(DeviceNameBuilder.class);
                add(MacAddressBuilder.class);
                add(FirmwareUpgradeBuilder.class);
                add(UnBindBuilder.class);
            }
        });
    }


    public interface Builder {
        SettingItem build(AppCompatActivity context, DeviceInfo device);
    }

    public static List<SettingItem> create(AppCompatActivity context, DeviceInfo device) {
        DeviceType deviceType = device.getDeviceType();
        List<Class<? extends SettingItem>> builderList = builderMaps.get(deviceType);
        List<SettingItem> settingItemList = new ArrayList<>();
        for (Class<? extends SettingItem> builderClass : builderList) {
            try {
                SettingItem settingItem = builderClass.newInstance();
                settingItem.setDeviceInfo(device);
                settingItem.setContext(context);
                settingItem.fetchItemConfigs();
                settingItemList.add(settingItem);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return settingItemList;
    }
}
