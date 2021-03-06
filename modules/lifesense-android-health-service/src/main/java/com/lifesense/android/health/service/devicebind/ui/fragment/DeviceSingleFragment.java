package com.lifesense.android.health.service.devicebind.ui.fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.application.BindReceiver;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.enums.BindState;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.common.fragment.argsbuilder.SerializableArgsBuilder;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

/**
 * 单设备展示
 */
@FragmentWithArgs
public class DeviceSingleFragment extends BaseFragment {
    @Arg(key = "DEVICE_INFO_APP", bundler = SerializableArgsBuilder.class)
    LSEDeviceInfoApp lseDeviceInfoApp;
    private ConnectSearchViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_single;
    }

    @Override
    protected void initData() {
        viewModel = getActivityViewModel(ConnectSearchViewModel.class);
        viewModel.setTitle(getString(R.string.scale_device_search_connect_title));
        getViewDataBinding().setVariable(BR.listener, this);
        getViewDataBinding().setVariable(BR.imageUrl, "https://files.lifesense.com/other/20201126/b8ce912acc7d41a3a85bef390dcc873a.png");
        getViewDataBinding().setVariable(BR.deviceInfo, lseDeviceInfoApp);
    }

    public void onClick(View v) {
        bindDevice();
    }

    Handler manHandler = new Handler(Looper.getMainLooper());

    public void bindDevice() {
        BleDeviceManager.getDefaultManager().stopSearch();
        final DeviceInfo lseDeviceInfo = lseDeviceInfoApp.getLSEDeviceInfo();
        BleDeviceManager.getDefaultManager().bind(lseDeviceInfo, new BindReceiver() {
            @Override
            public void onReceiveRandomNumberRequest() {
                viewModel.showInputCode(lseDeviceInfoApp);
            }

            @Override
            public void onReceiveBindState(BindState bindState) {
                if (bindState == BindState.BIND_SUCCESS) {
                    viewModel.bindSuccess(lseDeviceInfoApp);
                    PreferenceStorage.addBondDevice(lseDeviceInfo.getMac());
                    PreferenceStorage.cacheDeviceInfo(lseDeviceInfo.getMac(), lseDeviceInfo);
                } else if (bindState == BindState.RANDOM_NUMBER_MISS_MATCH) {
                    viewModel.showInputCode(lseDeviceInfoApp);
                } else {
                    Log.w("bindState", bindState.name());
                    viewModel.bindFail(lseDeviceInfoApp);
                    BleDeviceManager.getDefaultManager().unBind(lseDeviceInfo.getMac());
                }
            }

            @Override
            public void onReceiveDeviceIdRequest() {
                BleDeviceManager.getDefaultManager().inputDeviceId(lseDeviceInfo.getMac(), lseDeviceInfo.getMac().replace(":", ""));
            }

        });
        viewModel.bind(lseDeviceInfoApp);
    }
}
