package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.view.View;
import android.widget.CompoundButton;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


import com.lifesense.android.ble.device.band.model.config.Silence;
import com.lifesense.android.health.service.devicedetails.utils.OptionPickerUtil;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class SilenceConfigViewModel extends ConfigViewModel<Silence> {

    private LiveData<Boolean> enable = Transformations.map(getUpdateConfig(), input -> input.isEnable());

    private LiveData<Boolean> enableRaise = Transformations.map(getUpdateConfig(), input -> input.isEnableRaise());

    private LiveData<String> startTime = Transformations.map(getUpdateConfig(), input -> input.getStartHour() + ":" + input.getStartMins());

    private LiveData<String> endTime = Transformations.map(getUpdateConfig(), input -> input.getEndHour() + ":" + input.getEndMins());

    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public LiveData<Boolean> getEnableRaise() {
        return enableRaise;
    }

    public LiveData<String> getStartTime() {
        return startTime;
    }

    public LiveData<String> getEndTime() {
        return endTime;
    }

    public void onSilenceSwitchCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Silence silence = getUpdateConfig().getValue();
        if(silence.isEnable() != isChecked) {
            silence.setEnable(isChecked);
            updateConfig();
        }
    }

    public void onUpHandSwitchCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Silence silence = getUpdateConfig().getValue();
        if(silence.isEnableRaise() != isChecked) {
            silence.setEnableRaise(isChecked);
            updateConfig();
        }
    }

    public void onStartTimeClick(View view) {
        OptionPickerUtil.showTimePicker(view.getContext(), (hour, min) -> {
            Silence silence = getUpdateConfig().getValue();
            silence.setStartHour((short) hour);
            silence.setStartMins((short) min);
            updateConfig();
        });
    }

    public void onEndTimeClick(View view) {
        OptionPickerUtil.showTimePicker(view.getContext(), (hour, min) -> {
            Silence silence = getUpdateConfig().getValue();
            silence.setEndHour((short) hour);
            silence.setEndMins((short) min);
            updateConfig();
        });
    }

    @Override
    public String getTitleStr() {
        return "勿扰模式";
    }
}
