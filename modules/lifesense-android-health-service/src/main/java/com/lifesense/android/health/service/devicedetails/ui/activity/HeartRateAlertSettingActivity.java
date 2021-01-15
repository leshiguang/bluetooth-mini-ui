package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.HeartRateAlert;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.HeartRateAlertConfigViewModel;
/**
 * Create by qwerty
 * Create on 2020/6/9
 * 心率预警
 *
 * @author alexwu*/
public class HeartRateAlertSettingActivity extends BaseSettingActivity<HeartRateAlertConfigViewModel,HeartRateAlert> {

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_pedometer_heart_rate_alert_setting;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }
}
