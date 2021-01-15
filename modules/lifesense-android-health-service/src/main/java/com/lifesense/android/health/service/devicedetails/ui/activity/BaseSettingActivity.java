package com.lifesense.android.health.service.devicedetails.ui.activity;


import android.os.Bundle;

import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.core.serializer.AbstractConfig;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.ConfigViewModel;
import com.lifesense.android.health.service.util.ToastUtil;

/**
 * 基础设备设置页
 * @author alexwu
 * @param <T>
 */
public abstract class BaseSettingActivity<VM extends ConfigViewModel<T>,T extends AbstractConfig> extends BaseDataBindingActivity<VM> {

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.getConfigStatus().observe(this, configStatus -> {
            if(configStatus == ConfigStatus.SUCCESS) {
                onSettingSuccess();
            } else {
                onSettingFailed();
            }
        });
    }
    protected void onSettingSuccess() {
        ToastUtil.showCenterShowToast(BaseSettingActivity.this, "设置成功");
        dismissLoading();
    }

    protected void onSettingFailed() {
        ToastUtil.showCenterShowToast(BaseSettingActivity.this, "设置失败");
        dismissLoading();
    }

}
