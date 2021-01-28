package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.NightMode;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.NightModelConfigViewModel;
/**
 * Create by qwerty
 * Create on 2020/6/9
 * 夜间模式
 **/
public class NightModeSettingActivity extends BaseSettingActivity<NightModelConfigViewModel,NightMode> {

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_night_mode_setting;
    }

    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }

}
