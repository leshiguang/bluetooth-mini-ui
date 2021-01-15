package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.TimeFormat;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.TimeFormatConfigViewModel;

/**
 * Create by qwerty
 * Create on 2020/6/9
 * 时间制式
 **/
public class TimeFormatSettingActivity extends BaseSettingActivity<TimeFormatConfigViewModel, TimeFormat> {
    @Override
    protected int getContentView() {
        return R.layout.scale_activity_simple_linear_recyclerview;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }
}
