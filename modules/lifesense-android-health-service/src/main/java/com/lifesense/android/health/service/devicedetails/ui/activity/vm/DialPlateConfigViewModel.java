package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.common.collect.Lists;
import com.lifesense.android.ble.core.application.model.config.DialPlate;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.DialPlateRvAdapter;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class DialPlateConfigViewModel extends ConfigViewModel<DialPlate> {
    MutableLiveData<BaseDataBindingRvAdapter> adapter = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        DialPlateRvAdapter adapter = new DialPlateRvAdapter();
        adapter.setItems(Lists.newArrayList(DialPlate.DialPlateType.values()));
        adapter.setSelectedItem(getUpdateConfig().getValue().getType());
        this.adapter.setValue(adapter);
    }

    @Override
    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return adapter;
    }

    @Override
    public int getTitleId() {
        return R.layout.scale_activity_dial_peace_setting;
    }
}
