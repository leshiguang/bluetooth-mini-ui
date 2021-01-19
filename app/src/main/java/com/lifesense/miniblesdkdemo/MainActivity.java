package com.lifesense.miniblesdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.lifesense.android.health.service.common.ui.BaseActivity;
import com.lifesense.android.health.service.deviceconfig.ui.DeviceStatusListActivity;
import com.lifesense.utils.ImageUtil;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageUtil.init(this.getApplicationContext());
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
            if (checkPermission()) {
                startActivity(new Intent(this, DeviceStatusListActivity.class));
                finish();
            }
        }
    }


    private boolean checkPermission() {
        return checkReadPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
    }

}