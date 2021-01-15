package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.lifesense.android.ble.core.application.model.config.Call;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.DeviceMessageReminderRvAdapter;

/**
 * Create by qwerty
 * Create on 2021/1/6
 **/
public class MessageReminderViewModel extends ConfigViewModel<Call> {
    private MutableLiveData<BaseDataBindingRvAdapter> adapter = new MutableLiveData<>();
    private MutableLiveData<Boolean> enable = new MutableLiveData<>();

    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        DeviceMessageReminderRvAdapter adapter = new DeviceMessageReminderRvAdapter(getDeviceInfo().getValue().getMac());
        adapter.setItems(getConfigs().getValue());
        this.adapter.setValue(adapter);
        boolean enable = false;
        String packageName = context.getPackageName();
        String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        if (flat != null) {
            enable = flat.contains(packageName);
        }
        this.enable.setValue(enable);
    }

    @Override
    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return adapter;
    }

    public MutableLiveData<Boolean> getEnable() {
        return enable;
    }

    public void onClick(View view) {
        gotoNotificationAccessSetting(view.getContext());
    }
    private boolean gotoNotificationAccessSetting(Context context) {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                context.startActivity(intent);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}
