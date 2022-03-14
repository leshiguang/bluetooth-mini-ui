package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.text.TextUtils;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.lifesense.android.ble.device.band.model.config.Call;
import com.lifesense.android.ble.device.band.model.enums.VibrationMode;


/**
 * Create by qwerty
 * Create on 2021/1/21
 **/
public class CustomMessageReminderViewModel extends ConfigViewModel<Call> {
    LiveData<Boolean> enable = new MutableLiveData<>();

    MutableLiveData<String> packageName = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity activity) {
        super.init(activity);
        Call call = new Call();
        call.setEnable(false);
        call.setReminderType(Call.ReminderType.CUSTOM);
        call.setVibrationMode(VibrationMode.CONTINUOUS_VIBRATION);
        call.setVibrationLevel(9);
        call.setVibrationLevel1(9);
        call.setVibrationTime((short)60);
        this.setUpdateConfig(call);
        packageName.observe(activity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Call heartRateAlert = getUpdateConfig().getValue();
                heartRateAlert.setAppPackageName(s);
            }
        });
    }

    public void onSwitchChanged(CompoundButton button, boolean isChecked) {
        Call heartRateAlert = getUpdateConfig().getValue();
        if(heartRateAlert.isEnable() != isChecked || TextUtils.isEmpty(packageName.getValue())) {
            heartRateAlert.setEnable(isChecked);
            updateConfig();
        }
    }

    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public MutableLiveData<String> getPackageName() {
        return packageName;
    }
}
