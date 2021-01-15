package com.lifesense.android.health.service.devicedetails.ui.activity;

import android.content.Context;
import android.content.Intent;


import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.DeviceConfigsViewModel;

import static com.lifesense.android.health.service.devicedetails.ui.activity.vm.DeviceConfigsViewModel.EXTRA_MAC;

/**
 * Create by qwerty
 * Create on 2020/10/12
 **/
public class DeviceConfigsActivity extends BaseDataBindingActivity<DeviceConfigsViewModel> {

    public static Intent makeIntent(Context context, String mac) {
        return new Intent(context, DeviceConfigsActivity.class).putExtra(EXTRA_MAC, mac);
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_device_settings;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }
}
