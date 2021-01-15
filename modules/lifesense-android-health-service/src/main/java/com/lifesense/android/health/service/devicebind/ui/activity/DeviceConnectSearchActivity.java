package com.lifesense.android.health.service.devicebind.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;
import com.lifesense.android.health.service.util.DialogUtil;
import com.lifesense.android.health.service.util.SystemUtil;
import com.lifesense.android.health.service.util.dialog.DialogConfig;
public class DeviceConnectSearchActivity extends BaseDataBindingActivity<ConnectSearchViewModel> {
    private BroadcastReceiver mReceiver;

    public static Intent makeIntentWithDisplayProduct(Context context) {
        Intent intent = new Intent(context, DeviceConnectSearchActivity.class);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_device_connect_search;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        registerBluetoothStateReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkBlePermission();
    }
    private void registerBluetoothStateReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }

                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    switch (state) {
                        case BluetoothAdapter.STATE_ON:
                            viewModel.startSearchDevice();
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            viewModel.showBleDisable();
                            showEnableBlueToothDialog();
                            break;
                    }
                }
            }
        };
        registerReceiver(mReceiver, filter);
    }

    private void unregisterBluetoothStateReceivers() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


    private void showEnableBlueToothDialog() {
        String content = getString(R.string.scale_device_open_bluetooth_to_lifesense);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getInstance().dismissProcessDialog();
                BluetoothAdapter.getDefaultAdapter().enable();
            }
        };
        DialogUtil.getInstance().showTitleTwoBtnDialog(new DialogConfig.Builder(this)
                .setContent(content)
                .setConfirmBtn(getString(R.string.scale_device_heart_maintain_mode))
                .setCancelBtn(getString(R.string.scale_common_cancel))
                .setConfirmClickListener(onClickListener)
                .build());
    }

    private void showGpsDialog() {
        DialogUtil.getInstance().showTitleTwoBtnDialog(new DialogConfig.Builder(this)
                .setTitle(getString(R.string.scale_permissions_location_title))
                .setContent(getString(R.string.scale_permissions_location_open))
                .setCancelBtn(getString(R.string.scale_common_cancel))
                .setConfirmBtn(getString(R.string.scale_permissions_setting))
                .setConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.getInstance().dismissDialog();
                        SystemUtil.openLocationOpenActivity(DeviceConnectSearchActivity.this);
                    }
                }).build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBluetoothStateReceivers();
        if(viewModel != null) {
            viewModel.cancelBind();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void checkBlePermission() {
        if (!SystemUtil.isProviderEnabledGps(this)) {
            showGpsDialog();
            return;
        }

        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            showEnableBlueToothDialog();
            return;
        }
    }
}
