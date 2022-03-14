package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


import com.lifesense.android.ble.device.band.model.config.HeartRateAlert;
import com.lifesense.android.health.service.devicedetails.utils.OptionPickerUtil;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class HeartRateAlertConfigViewModel extends ConfigViewModel<HeartRateAlert> {
    private LiveData<Boolean> enable = Transformations.map(getUpdateConfig(), input -> input.isEnable());

    private LiveData<String> range = Transformations.map(getUpdateConfig(), input -> input.getRangeLeft() + "~" + input.getRangeRight());


    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public LiveData<String> getRange() {
        return range;
    }

    public void onAlertSwitchCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        HeartRateAlert heartRateAlert = getUpdateConfig().getValue();
        if(heartRateAlert.isEnable() != isChecked) {
            heartRateAlert.setEnable(isChecked);
            updateConfig();
        }
    }

    public void onHeartAlertRangeClick(View view) {
        OptionPickerUtil.showRangePicker(view.getContext(), 20, 255, 20, 255, (start, end) -> {
            HeartRateAlert heartRateAlert = new HeartRateAlert();
            heartRateAlert.setRangeLeft(start);
            heartRateAlert.setRangeRight(end);
            heartRateAlert.setEnable(true);
            // 1常规、2 运动
            heartRateAlert.setType(2);
            updateConfig();
        });
    }

    @Override
    public String getTitleStr() {
        return "心率预警设置";
    }
}
