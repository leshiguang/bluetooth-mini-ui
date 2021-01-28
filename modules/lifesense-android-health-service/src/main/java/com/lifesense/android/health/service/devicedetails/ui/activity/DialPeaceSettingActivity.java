package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.DialPlate;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.DialPlateConfigViewModel;

/**
 * Create by qwerty
 * Create on 2020/6/9
 * 表盘样式设置
 **/
public class DialPeaceSettingActivity extends BaseSettingActivity<DialPlateConfigViewModel, DialPlate> {
    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }
    @Override
    protected int getContentView() {
        return R.layout.scale_activity_dial_peace_setting;
    }
}
