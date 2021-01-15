package com.lifesense.android.health.service.devicebind.ui.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.common.collect.Lists;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.common.ui.BaseViewModel;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceFailFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceBluetoothDisableFragment;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceInputCodeFragment;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceInputCodeFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceMultiFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DevicePairingFragment;
import com.lifesense.android.health.service.devicebind.ui.fragment.DevicePairingFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceSearchFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceSingleFragmentBuilder;
import com.lifesense.android.health.service.devicebind.ui.fragment.DeviceSuccessFragmentBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2020/12/29
 **/
public class ConnectSearchViewModel extends BaseViewModel {
    private MutableLiveData<Fragment> currentFragment = new MutableLiveData<Fragment>();
    private MutableLiveData<String> title = new MutableLiveData<>();

    @Override
    public void init(AppCompatActivity activity) {
        startSearchDevice();
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public MutableLiveData<Fragment> getCurrentFragment() {
        return currentFragment;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void startSearchDevice() {
        if (!BleDeviceManager.getDefaultManager().isBluetoothEnabled()) {
            showBleDisable();
            return;
        }
        search();
    }

    public void search() {
        executeOp(Op.SEARCH, null);
    }

    public void bind(LSEDeviceInfoApp deviceInfoApp) {
        executeOp(Op.BIND, Lists.newArrayList(deviceInfoApp));
    }

    public void bindSuccess(LSEDeviceInfoApp deviceInfoApp) {
        executeOp(Op.BIND_SUCCESS, Lists.newArrayList(deviceInfoApp));
    }

    public void bindFail(LSEDeviceInfoApp deviceInfoApp) {
        executeOp(Op.BIND_FAIL, Lists.newArrayList(deviceInfoApp));
    }

    public void show(List<LSEDeviceInfoApp> deviceInfoAppList) {
        int size = deviceInfoAppList.size();
        switch (size) {
            case 0:
                bindFail(null);
                break;
            case 1:
                showSingleDevice(deviceInfoAppList);
                break;
            default:
                showMultiDevice(deviceInfoAppList);
                break;
        }
    }

    public void showMultiDevice(List<LSEDeviceInfoApp> deviceInfoAppList) {
        executeOp(Op.SHOW_MULTI_DEVICE, deviceInfoAppList);
    }

    public void showSingleDevice(List<LSEDeviceInfoApp> deviceInfoAppList) {
        executeOp(Op.SHOW_SINGLE_DEVICE, deviceInfoAppList);
    }

    public void showBleDisable() {
        executeOp(Op.DISABLE_BLE, null);
    }

    public void showInputCode(LSEDeviceInfoApp deviceInfoApp) {
        executeOp(Op.INPUT_CODE, Lists.newArrayList(deviceInfoApp));
    }

    public void cancelBind() {
        if(currentFragment.getValue() instanceof DevicePairingFragment) {
            DevicePairingFragment devicePairingFragment = (DevicePairingFragment) currentFragment.getValue();
            devicePairingFragment.cancelBind();
        }

        if(currentFragment.getValue() instanceof DeviceInputCodeFragment) {
            DeviceInputCodeFragment deviceInputCodeFragment = (DeviceInputCodeFragment) currentFragment.getValue();
            deviceInputCodeFragment.cancelBind();
        }
    }

    private void executeOp(Op op, List<LSEDeviceInfoApp> deviceInfoAppList) {
        setCurrentFragment(op.createFragment(deviceInfoAppList));
    }

    private void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment.postValue(currentFragment);
    }


    private enum Op {
        SEARCH {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return new DeviceSearchFragmentBuilder().build();
            }
        },
        BIND {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DevicePairingFragmentBuilder.newDevicePairingFragment(deviceInfoApp.get(0), "https://files.lifesense.com/other/20201126/b8ce912acc7d41a3a85bef390dcc873a.png");
            }
        },
        BIND_FAIL {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DeviceFailFragmentBuilder.newDeviceFailFragment("https://files.lifesense.com/other/20201126/b8ce912acc7d41a3a85bef390dcc873a.png");
            }
        },
        BIND_SUCCESS {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DeviceSuccessFragmentBuilder.newDeviceSuccessFragment(deviceInfoApp.get(0).getLSEDeviceInfo(), "https://files.lifesense.com/other/20201126/b8ce912acc7d41a3a85bef390dcc873a.png", deviceInfoApp.get(0));
            }
        },
        SHOW_SINGLE_DEVICE {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DeviceSingleFragmentBuilder.newDeviceSingleFragment(deviceInfoApp.get(0));
            }
        },
        SHOW_MULTI_DEVICE {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DeviceMultiFragmentBuilder.newDeviceMultiFragment((ArrayList<LSEDeviceInfoApp>) deviceInfoApp);
            }
        },
        DISABLE_BLE {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return new DeviceBluetoothDisableFragment();
            }
        },
        INPUT_CODE {
            @Override
            public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
                return DeviceInputCodeFragmentBuilder.newDeviceInputCodeFragment(deviceInfoApp.get(0));
            }
        };

        public Fragment createFragment(List<LSEDeviceInfoApp> deviceInfoApp) {
            return new DeviceSearchFragmentBuilder().build();
        }
    }
}
