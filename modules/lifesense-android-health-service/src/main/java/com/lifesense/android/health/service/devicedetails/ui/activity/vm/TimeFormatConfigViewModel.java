package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.lifesense.android.ble.core.application.model.config.TimeFormat;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.TimeFormatRvAdapter;

import java.util.Arrays;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class TimeFormatConfigViewModel extends ConfigViewModel<TimeFormat>{
    MutableLiveData<BaseDataBindingRvAdapter> adapter = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        TimeFormatRvAdapter adapter = new TimeFormatRvAdapter();
        adapter.setItems(Arrays.asList(0, 1));
        TimeFormat timeFormat = getUpdateConfig().getValue();
        adapter.setSelectedItem(timeFormat.getType());
        adapter.setSelectListener((view, pos) -> {
            timeFormat.setType(pos);
            updateConfig();
        });
        this.adapter.setValue(adapter);
    }

    @Override
    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return adapter;
    }

    @Override
    public String getTitleStr() {
        return "时间制式设置";
    }
}
