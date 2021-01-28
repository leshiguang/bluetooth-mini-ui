package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.Silence;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.SilenceConfigViewModel;

/**
 * Create by qwerty
 * Create on 2020/6/9
 * 勿扰模式
 *
 * @author alexwu*/
public class SilenceSettingActivity extends BaseSettingActivity<SilenceConfigViewModel, Silence> {

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_no_disturb_mode_setting;
    }


    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }

}
