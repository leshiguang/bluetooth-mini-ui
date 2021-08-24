package com.lifesense.android.health.service.devicebind.ui.fragment;

import android.util.Log;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.application.BindReceiver;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.enums.BindState;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.common.fragment.argsbuilder.ListArgsBuilder;
import com.lifesense.android.health.service.devicebind.adapter.FindDeviceResultRvAdapter;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 多设备展示
 */
@FragmentWithArgs
public class DeviceMultiFragment extends BaseFragment {

    @Arg(key = "DEVICE_INFO_APP_LIST", bundler = ListArgsBuilder.class)
    ArrayList<LSEDeviceInfoApp> deviceList;
    FindDeviceResultRvAdapter adapter;
    private ConnectSearchViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_multi;
    }

    @Override
    protected void initData() {
        viewModel = getActivityViewModel(ConnectSearchViewModel.class);
        viewModel.setTitle(getString(R.string.scale_device_search_connect_title));
        adapter = new FindDeviceResultRvAdapter();
        adapter.setItems(changeNearest(deviceList));
        getViewDataBinding().setVariable(BR.adapter, adapter);
        getViewDataBinding().setVariable(BR.listener, this);
    }

    public void onClick(View v) {
        bindDevice(adapter.getSelectedItem());
    }

    public void bindDevice(LSEDeviceInfoApp lseDeviceInfoApp) {
        BleDeviceManager.getDefaultManager().stopSearch();
        final DeviceInfo lseDeviceInfo = lseDeviceInfoApp.getLSEDeviceInfo();
        BleDeviceManager.getDefaultManager().bindWithRecovery(lseDeviceInfo, new BindReceiver() {
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

                } else if(bindState == BindState.RANDOM_NUMBER_MISS_MATCH){
                    viewModel.showInputCode(lseDeviceInfoApp);
                }else {
                    Log.w("bindState", bindState.name());
                    viewModel.bindFail(lseDeviceInfoApp);
                    BleDeviceManager.getDefaultManager().unBind(lseDeviceInfo.getMac());
                }
            }

            @Override
            public void onReceiveDeviceIdRequest() {
                BleDeviceManager.getDefaultManager().inputDeviceId(lseDeviceInfo.getMac(), lseDeviceInfo.getMac().replace(":",""));
            }

        });
        viewModel.bind(lseDeviceInfoApp);
    }

    private static ArrayList<LSEDeviceInfoApp> changeNearest(List<LSEDeviceInfoApp> mDeviceList) {
        ArrayList<LSEDeviceInfoApp> lseDeviceInfoApps = new ArrayList<>(mDeviceList);
        Collections.sort(lseDeviceInfoApps, new Comparator<LSEDeviceInfoApp>() {
            @Override
            public int compare(LSEDeviceInfoApp o1, LSEDeviceInfoApp o2) {
                return Math.abs(o1.getRssi()) > Math.abs(o2.getRssi()) ? 1 : -1;
            }
        });
        return lseDeviceInfoApps;
    }
}
