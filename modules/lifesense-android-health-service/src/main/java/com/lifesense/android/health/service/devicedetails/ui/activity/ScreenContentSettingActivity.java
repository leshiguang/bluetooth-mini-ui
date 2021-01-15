package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.Page;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.ScreenContentConfigViewModel;

/**
 * Create by qwerty
 * Create on 2020/6/9
 * 自定义屏幕
 *
 * @author alexwu*/
public class ScreenContentSettingActivity extends BaseSettingActivity<ScreenContentConfigViewModel, Page> {

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_simple_linear_recyclerview;
    }
}
