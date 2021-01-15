package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.common.collect.Lists;
import com.lifesense.android.ble.core.application.model.config.TargetEncourage;
import com.lifesense.android.health.service.devicedetails.utils.OptionPickerUtil;

import java.util.List;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class EncourageConfigViewModel extends ConfigViewModel<TargetEncourage> {
    LiveData<Boolean> enable = Transformations.map(getUpdateConfig(), input -> input.isEnable());

    LiveData<String> targetType = Transformations.map(getUpdateConfig(), input -> {
        int type = input.getTargetType();
        if (type == 1) {
            return "步数";
        } else if (type == 2) {
            return "卡路里";
        } else {
            return "距离";
        }
    });

    LiveData<String> target = Transformations.map(getUpdateConfig(), input -> String.valueOf(input.getTarget()));

    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public LiveData<String> getTargetType() {
        return targetType;
    }

    public LiveData<String> getTarget() {
        return target;
    }


    public void onEncourageTypeClick(View view) {
        showEncourageTypePicker(view.getContext());
    }

    public void onEncourageValueClick(View view) {
        showEncourageValuePicker(view.getContext());
    }

    public void onTargetEncourageSwitchChanged(CompoundButton compoundButton, boolean isChecked) {
        TargetEncourage targetEncourage = getUpdateConfig().getValue();
        if (targetEncourage.isEnable() != isChecked) {
            targetEncourage.setEnable(isChecked);
            updateConfig();
        }
    }

    private void showEncourageTypePicker(Context context) {
        List<String> encourageTextList = Lists.newArrayList("步数", "卡路里", "距离");
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            setTargetType(options1 + 1);
        }).build();
        optionsPickerView.setPicker(encourageTextList);
        optionsPickerView.show();
    }

    private void showEncourageValuePicker(Context context) {
        int start = 0;
        int end = 5000;
        int space = 10;
        switch (getUpdateConfig().getValue().getTargetType()) {
            case 1:
                start = 1000;
                end = 50000;
                space = 1000;
                break;
            case 2:
                start = 1;
                end = 30;
                space = 1;
                break;
            case 3:
                start = 100;
                end = 1000;
                space = 10;
                break;

        }

        OptionPickerUtil.showNumberPicker(context, start, end, space, num -> {
            setTarget(num);
        });
    }

    private void setTargetType(int type) {
        TargetEncourage targetEncourage = getUpdateConfig().getValue();
        targetEncourage.setTargetType(type);
        updateConfig();
    }

    private void setTarget(int value) {
        TargetEncourage targetEncourage = getUpdateConfig().getValue();
        targetEncourage.setTarget(value);
        updateConfig();
    }

}
