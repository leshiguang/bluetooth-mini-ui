package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.TargetEncourage;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.EncourageConfigViewModel;
/**
 * Create by qwerty
 * Create on 2020/6/9
 * 目标设置
 **/
public class EncourageSingleSettingActivity extends BaseSettingActivity<EncourageConfigViewModel,TargetEncourage> {

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_encourage_setting;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }
}
