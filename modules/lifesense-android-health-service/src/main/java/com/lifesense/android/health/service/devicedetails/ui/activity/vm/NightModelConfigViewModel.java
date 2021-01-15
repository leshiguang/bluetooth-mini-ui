package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.lifesense.android.ble.core.application.model.config.NightMode;
import com.lifesense.android.health.service.devicedetails.utils.OptionPickerUtil;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class NightModelConfigViewModel extends ConfigViewModel<NightMode> {

    LiveData<Boolean> enable = Transformations.map(getUpdateConfig(), input -> input.isEnable());

    LiveData<String> startTime = Transformations.map(getUpdateConfig(), input -> input.getStartHour() + ":" + input.getStartMins());

    LiveData<String> endTime = Transformations.map(getUpdateConfig(), input -> input.getEndHour() + ":" + input.getEndMins());

    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public LiveData<String> getStartTime() {
        return startTime;
    }

    public LiveData<String> getEndTime() {
        return endTime;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        NightMode nightMode = getUpdateConfig().getValue();
        if (isChecked != nightMode.isEnable()) {
            nightMode.setEnable(isChecked);
            updateConfig();
        }
    }

    public void onStartTimeClick(View view) {
        OptionPickerUtil.showTimePicker(view.getContext(), (hour, min) -> {
            NightMode nightMode = getUpdateConfig().getValue();
            nightMode.setStartHour((short) hour);
            nightMode.setStartMins((short) min);
            updateConfig();
        });
    }

    public void onEndTimeClick(View view) {
        OptionPickerUtil.showTimePicker(view.getContext(), (hour, min) -> {
            NightMode nightMode = getUpdateConfig().getValue();
            nightMode.setEndHour((short) hour);
            nightMode.setEndMins((short) min);
            updateConfig();
        });
    }
}
