package com.lifesense.android.health.service.deviceota;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.lifesense.android.ble.core.valueobject.DeviceInfo;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseActivity;
import com.lifesense.android.health.service.devicedetails.widget.AlertDialogFragment;
import com.lifesense.android.health.service.devicedetails.widget.CircleProgressBar;
import com.lifesense.android.health.service.deviceota.model.UpgradeState;
import com.lifesense.android.health.service.util.Spanny;

/**
 * Create by qwerty
 * Create on 2020/6/9
 **/
public class TestDeviceUpgradeActivity extends BaseActivity implements View.OnClickListener {

    private static final String DEVICE_ID_EXTRA = "device_id_extra";

    private static final String FIRMWARE_INFO_EXTRA = "firmware_info_extra";
//
//    public static Intent makeIntent(Context context, String deviceId, FirmwareInfo firmwareInfo) {
//        return new Intent(context, TestDeviceUpgradeActivity.class).putExtra(DEVICE_ID_EXTRA, deviceId).putExtra(FIRMWARE_INFO_EXTRA, (Parcelable) firmwareInfo);
//    }

    private UpgradeState currentUpgradeState;

    private DeviceInfo device;

    private CircleProgressBar upgradeProgressBar;

    private TextView tvUpgradeProgressText;

    private TextView tvUpgradeStateMsg;

    private TextView tvRetry;

    private TextView tvCancelUpgrade;

    private TextView tvStartUpgrade;

//    private FirmwareInfo firmwareInfo;

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_device_upgrade;
    }

    @Override
    protected boolean showNav() {
        return true;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(getString(R.string.scale_device_firmware_upgrade_title));
        upgradeProgressBar = findViewById(R.id.upgrade_progress_bar);
        tvUpgradeProgressText = findViewById(R.id.tv_upgrade_progress_text);
        tvUpgradeStateMsg = findViewById(R.id.tv_upgrade_state_msg);
        tvStartUpgrade = findViewById(R.id.tv_start_upgrade);
        tvStartUpgrade.setOnClickListener(this);
        tvRetry = findViewById(R.id.tv_retry);
        tvRetry.setOnClickListener(this);
        tvCancelUpgrade = findViewById(R.id.tv_cancel_upgrade);
        tvCancelUpgrade.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        final String deviceId = getIntent().getStringExtra(DEVICE_ID_EXTRA);
//        firmwareInfo = getIntent().getParcelableExtra(FIRMWARE_INFO_EXTRA);
//        if (deviceId != null) {
//            List<Device> bondedDevices = LZDeviceService.getInstance().getBondedDevices();
//            if (CollectionUtils.isNotEmpty(bondedDevices)) {
//                for (Device device : bondedDevices) {
//                    if (device.getId().equalsIgnoreCase(deviceId)) {
//                        this.device = device;
//                        break;
//                    }
//                }
//            }
//        }
        UpgradeState.UPGRADING.setProgress(0);
        startUpgrade();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_start_upgrade || id == R.id.tv_retry) {
            startUpgrade();
        } else if (id == R.id.tv_cancel_upgrade) {
            showCancelUpgradeDialog();
        }
    }

    /**
     * 从服务器下载固件，进度占总进度的5%
     */
    private void downloadFirmware() {
//        if (firmwareInfo == null) {
//            return;
//        }
//        DownloadRequest downloadRequest = new DownloadRequest(FileUtils.getFilePath(this, firmwareInfo.getFileName()), firmwareInfo.getFileUrl());
//        downloadRequest.execute(new IDownloadRequestCallback() {
//            @Override
//            public void onDownloading(int i) {
//                setCurrentUpgradeState(UpgradeState.UPGRADING.setProgress(mapPercent5(i)));
//            }
//
//            @Override
//            public void onRequestSuccess(DownloadResponse downloadResponse) {
//                startUpgrade();
//            }
//
//            @Override
//            public void onRequestError(int i, String s, DownloadResponse downloadResponse) {
//                ToastUtil.showSingletonToast(TestDeviceUpgradeActivity.this,getString(R.string.scale_connect_interrupt_msg));
//                setCurrentUpgradeState(UpgradeState.UPGRADE_INTERRUPT);
//            }
//        });
    }

    /**
     * 上传固件文件到设备，进度占总进度的95%
     */
    private void startUpgrade() {
        if (device != null) {
//            LZDeviceService.getInstance().upgradeDeviceFirmware(device.getId(), firmwareInfo.getFileName(), new UpgradeStateListener() {
//                @Override
//                public void onStart() {
//                    setCurrentUpgradeState(UpgradeState.UPGRADING);
//                }
//
//                @Override
//                public void onProgress(int i) {
//                    setCurrentUpgradeState(UpgradeState.UPGRADING.setProgress(5 + mapPercent95(i)));
//                }
//
//                @Override
//                public void onFinish(boolean b, int i, String s) {
//                    if (b) {
//                        setCurrentUpgradeState(UpgradeState.UPGRADE_SUCCESS);
//                    } else {
//                        ToastUtil.showSingletonToast(TestDeviceUpgradeActivity.this,getString(R.string.scale_connect_interrupt_msg));
//                        setCurrentUpgradeState(UpgradeState.UPGRADE_INTERRUPT);
//                    }
//                }
//            });
        }
    }

    private void showCancelUpgradeDialog() {
        final AlertDialogFragment alertDialogFragment = new AlertDialogFragment
                .Builder()
                .setTitle(getString(R.string.scale_tips))
                .setMsg(getString(R.string.scale_stop_upgrade_msg))
                .setNegativeClickListener(getString(R.string.scale_resume), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveClickListener(getString(R.string.scale_stop), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelUpgrade();
                    }
                })
                .build();
        alertDialogFragment.show(getSupportFragmentManager());
    }

    private int mapPercent5(int progress) {
        return (int) (5f / 100f * progress);
    }

    private int mapPercent95(int progress) {
        return (int) (95f / 100f * progress);
    }

    private void cancelUpgrade() {
        if (device != null) {
//            LZDeviceService.getInstance().interruptUpgradeDeviceFirmware(device.getId());
            finish();
        }
    }

    private void setCurrentUpgradeState(UpgradeState upgradeState) {
        currentUpgradeState = upgradeState;
        if (!TextUtils.isEmpty(currentUpgradeState.getIcon(this))) {
            tvUpgradeProgressText.setText(currentUpgradeState.getIcon(this));
        } else {
            Spanny progressSpanny = new Spanny();
            progressSpanny.append(String.valueOf(currentUpgradeState.getProgress()), new AbsoluteSizeSpan(47, true));
            progressSpanny.append("%", new AbsoluteSizeSpan(27, true));
            tvUpgradeProgressText.setText(progressSpanny);
        }
        upgradeProgressBar.setProgress(currentUpgradeState.getProgress());
        upgradeProgressBar.setCircleColor(currentUpgradeState.getCircleColor());
//        String latelyHardwareVersion = firmwareInfo != null ? firmwareInfo.getVersion() : "";
        switch (currentUpgradeState) {
            case UPGRADE_INFO:
//                String currentHardwareVersion = device != null ? device.getOtaVersion() : "";
//                tvUpgradeStateMsg.setText(this.currentUpgradeState.getMsg(this, latelyHardwareVersion, currentHardwareVersion));
                tvRetry.setVisibility(View.GONE);
                tvCancelUpgrade.setVisibility(View.GONE);
                tvStartUpgrade.setVisibility(View.VISIBLE);
                break;
            case UPGRADING:
                tvUpgradeStateMsg.setText(currentUpgradeState.getMsg(this));
                tvRetry.setVisibility(View.GONE);
                tvCancelUpgrade.setVisibility(View.VISIBLE);
                tvStartUpgrade.setVisibility(View.GONE);
                break;
            case UPGRADE_INTERRUPT:
                tvUpgradeStateMsg.setText(currentUpgradeState.getMsg(this));
                tvRetry.setVisibility(View.VISIBLE);
                tvCancelUpgrade.setVisibility(View.VISIBLE);
                tvStartUpgrade.setVisibility(View.GONE);
                break;
            case UPGRADE_SUCCESS:
//                tvUpgradeStateMsg.setText(currentUpgradeState.getMsg(this, latelyHardwareVersion));
                tvRetry.setVisibility(View.GONE);
                tvCancelUpgrade.setVisibility(View.GONE);
                tvStartUpgrade.setVisibility(View.GONE);
                break;
            default:
                tvRetry.setVisibility(View.GONE);
                tvCancelUpgrade.setVisibility(View.GONE);
                tvStartUpgrade.setVisibility(View.GONE);
                break;
        }
    }
}
