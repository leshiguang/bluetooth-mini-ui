package com.lifesense.android.health.service.devicedetails.item.builder;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.deviceota.ui.activity.DeviceUpgradeActivity;


/**
 * Create by qwerty
 * Create on 2020/10/27
 **/
public class FirmwareUpgradeBuilder extends SettingItem {
    private static final int FIRMWARE_UPGRADE_REQ = 1002;


    @Override
    public String getTitle() {
        return context.getString(R.string.scale_firmware_update);
    }

    @Override
    public String getValue() {
        return deviceInfo.getSoftwareVersion();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public Class getTargetAction() {
        return null;
    }


//    @Override
//    public SettingItem build(AppCompatActivity context, DeviceInfo device) {
//        return new SettingItem.Builder()
//                .setTitle()
//                .setValue(device.getFirmwareVersion())
//                .setShowArrow(true)
//                .setOnBindViewHolderCallback(new SettingItem.OnBindViewHolderCallback() {
//                    @Override
//                    public void onBindViewHolder(SettingItem settingItem) {
////                        LZDeviceService.getInstance().checkDeviceFirmwareUpgrade(device.getMac(), new OnCheckUpgradeCallback() {
////                            @Override
////                            public void onCheckUpgradeSuccess(FirmwareInfo firmwareInfo) {
////                                if(firmwareInfo != null) {
////                                    settingItem.setShowTag(true);
////                                    settingItem.setTag("有新的升级");
////                                }
////                            }
////
////                            @Override
////                            public void onCheckUpgradeFail(int i, String s) {
////
////                            }
////                        });
//
//                    }
//                })
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        LZDeviceService.getInstance().checkDeviceFirmwareUpgrade(device.getId(), new OnCheckUpgradeCallback() {
////                            @Override
////                            public void onCheckUpgradeSuccess(FirmwareInfo firmwareInfo) {
////                                    context.startActivityForResult(DeviceUpgradeActivity.makeIntent(context, device.getId(), firmwareInfo), FIRMWARE_UPGRADE_REQ);
////                            }
////
////                            @Override
////                            public void onCheckUpgradeFail(int i, String s) {
////                                ToastUtil.showCenterShowToast(context, "没有更新");
////                            }
////                        });
//                    }
//                })
//                .setOnActivityResultCallback(new SettingItem.OnActivityResultCallback() {
//                    @Override
//                    public void onActivityResult(SettingItem settingItem, int requestCode, int resultCode, @Nullable Intent data) {
//                        if(requestCode == FIRMWARE_UPGRADE_REQ && data != null) {
//                            String firmwareVersion = data.getStringExtra(DeviceUpgradeActivity.FIRMWARE_VERSION_RESULT);
//                            settingItem.setValue(firmwareVersion);
//                            settingItem.setShowTag(false);
//                        }
//                    }
//                })
//                .build();

}
