package com.lifesense.android.health.service.devicebind.ui.fragment;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;

/**
 * 设备绑定中展示
 */
@FragmentWithArgs
public class DevicePairingFragment extends BaseFragment {
    @Arg(key = "IMG_URL")
    String imgUrl;
    @Arg(key = "DEVICE_INFO")
    LSEDeviceInfoApp deviceInfo;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_pairing;
    }

    @Override
    protected void initData() {
        getActivity().setTitle(getString(R.string.scale_device_search_connect_title));
        getViewDataBinding().setVariable(BR.imageUrl, imgUrl);
        getViewDataBinding().setVariable(BR.deviceName, deviceInfo.getDeviceName());

    }
    public void cancelBind() {
        BleDeviceManager.getDefaultManager().unBind(deviceInfo.getLSEDeviceInfo().getMac());
    }
}
