package com.lifesense.android.health.service.devicedetails.item.builder;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.devicedetails.item.SettingFactory;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.widget.AlertDialogFragment;
import com.lifesense.android.health.service.util.TaskScheduler;
import com.lifesense.android.health.service.util.ToastUtil;

/**
 * Create by qwerty
 * Create on 2020/10/27
 **/
public class UnBindBuilder extends SettingItem {
    AlertDialogFragment alertDialogFragment;

    @Override
    public String getTitle() {
        return "解除绑定";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.Button;
    }

    @Override
    public void onClick(View v) {
        alertDialogFragment = new AlertDialogFragment.Builder()
                .setTitle("提示")
                .setMsg("确认解除绑定?")
                .setPositiveClickListener("", v1 -> TaskScheduler.getInstance().exectue(() -> {
                    if (BleDeviceManager.getDefaultManager().unBind(deviceInfo.getMac())) {
                        if (!(context.isDestroyed() || context.isFinishing())) {
                            context.runOnUiThread(() -> {
                                alertDialogFragment.dismiss();
                                context.finish();
                            });

                        }
                    } else {
                        context.runOnUiThread(() -> ToastUtil.showCenterShowToast(context, "解绑失败"));
                    }
                })).build();

        alertDialogFragment.show(context.getSupportFragmentManager());
    }

    @Override
    public Class getTargetAction() {
        return null;
    }


}
