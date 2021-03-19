package com.lifesense.miniblesdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.lifesense.android.health.service.common.ui.BaseActivity;
import com.lifesense.android.health.service.deviceconfig.ui.DeviceStatusListActivity;
import com.lifesense.android.health.service.util.ToastUtil;
import com.lifesense.utils.ImageUtil;

public class MainActivity extends BaseActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageUtil.init(this.getApplicationContext());
        Object a = null;
        try {
            a.hashCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(checkPermission()) {
            startActivity(new Intent(this, DeviceStatusListActivity.class));
            finish();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if(grantResults != null) {
                boolean flag = true;
                String leakPermission = "";
                for (int i = 0;i < grantResults.length; i++) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        leakPermission = permissions[i];
                        flag = false;
                    }
                }
                if(flag) {
                    startActivity(new Intent(this, DeviceStatusListActivity.class));
                    finish();
                } else {
                    ToastUtil.showCenterShowToast(this,"缺少权限:" + leakPermission);
                }
            }

        }
    }


    private boolean checkPermission() {
        return checkReadPermission(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
    }

}