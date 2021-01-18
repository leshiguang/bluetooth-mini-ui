package com.lifesense.android.health.service.devicebind.ui.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;


import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.common.fragment.argsbuilder.SerializableArgsBuilder;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;
import com.lifesense.android.health.service.util.DeviceUtils;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;

/**
 * 设备绑定成功展示
 */
@FragmentWithArgs
public class DeviceSuccessFragment extends BaseFragment {
    @Arg(key = "IMG_URL")
    String imgUrl;
    @Arg(key = "DEVICE_INFO_APP", bundler = SerializableArgsBuilder.class)
    LSEDeviceInfoApp lseDeviceInfoApp;
    @Arg(key = "DEVICE_INFO", bundler = SerializableArgsBuilder.class)
    DeviceInfo device;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_success;
    }

    @Override
    protected void initData() {
        getActivityViewModel(ConnectSearchViewModel.class).setTitle(getString(R.string.scale_device_search_connect_title));
        Toast.makeText(getContext(),getStringById(R.string.scale_bind_successful),1000);
        getViewDataBinding().setVariable(BR.listener,this);
        getViewDataBinding().setVariable(BR.imageUrl,imgUrl);

    }
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvBottom) {
            getActivity().startActivityForResult( DeviceUtils.getDeviceSettingsIntent(getActivity(),device.getMac()), Activity.RESULT_OK);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }
}
