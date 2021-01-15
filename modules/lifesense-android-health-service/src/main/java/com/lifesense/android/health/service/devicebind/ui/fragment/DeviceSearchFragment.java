package com.lifesense.android.health.service.devicebind.ui.fragment;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;

import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 搜索设备
 */
@FragmentWithArgs
public class DeviceSearchFragment extends BaseFragment {
    private List<LSEDeviceInfoApp> mDeviceList = new ArrayList<>();
    private long lastFindTime;
    private static final int LONGEST_SEARCH_SECOND = 30;
    private ConnectSearchViewModel connectSearchViewModel;
    private Disposable disposable;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_search;
    }

    @Override
    protected void initData() {
        connectSearchViewModel = getActivityViewModel(ConnectSearchViewModel.class);
        connectSearchViewModel.setTitle("");
    }

    @Override
    public void onShowChanged(boolean isShow) {
        if (isShow) {
            startSearchDevice();
        } else {
            stopSearchDevice();
        }
    }

    private void startSearchDevice() {
        getViewDataBinding().setVariable(BR.start, true);
        startCountTimer();
        BleDeviceManager.getDefaultManager().search(15000, new Consumer<DeviceInfo>() {
            @Override
            public void accept(DeviceInfo deviceInfo) throws Exception {
                if (deviceInfo == null) {
                    return;
                }

                LSEDeviceInfoApp info = new LSEDeviceInfoApp();
                info.setMacAddress(deviceInfo.getMac());

                lastFindTime = System.currentTimeMillis();
                info.setDeviceName(deviceInfo.getDeviceName());
                info.setRssi(deviceInfo.getRSSI());
                info.setLSEDeviceInfo(deviceInfo);
                if (canBeInsertIntoDeviceList(info)) {
                    mDeviceList.add(info);
                }
            }

        });
    }

    private void stopSearchDevice() {
        disposable.dispose();
        getViewDataBinding().setVariable(BR.start, false);
        BleDeviceManager.getDefaultManager().stopSearch();
    }

    private boolean canBeInsertIntoDeviceList(LSEDeviceInfoApp info) {
        boolean ret = true;
        //判断是不是已经加入了list
        for (int i = 0; i < mDeviceList.size(); i++) {
            if (mDeviceList.get(i).getMacAddress().equalsIgnoreCase(info.getMacAddress())) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    private void startCountTimer() {
        disposable = Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong >= LONGEST_SEARCH_SECOND && mDeviceList.size() == 0 || lastFindTime != 0 && System.currentTimeMillis() - lastFindTime > 3 * 1000 && mDeviceList.size() > 0) {
                    Collections.sort(mDeviceList);
                    stopSearchDevice();
                    connectSearchViewModel.show(mDeviceList);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopSearchDevice();
    }
}
