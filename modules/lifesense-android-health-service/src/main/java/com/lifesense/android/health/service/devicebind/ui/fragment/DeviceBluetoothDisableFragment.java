package com.lifesense.android.health.service.devicebind.ui.fragment;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;

/**
 * 蓝牙关闭
 */
public class DeviceBluetoothDisableFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_bluetooth_disable;
    }

    @Override
    public void initData() {
        getActivityViewModel(ConnectSearchViewModel.class).setTitle("");
    }
}
