package com.lifesense.android.health.service.devicedetails.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.config.WeightUnitConfig;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.adapter.UnitChoiceRvAdapter;
import com.lifesense.android.health.service.common.ui.BaseActivity;
import com.lifesense.android.health.service.devicedetails.model.Unit;
import com.lifesense.android.health.service.devicedetails.model.UnitCategory;
import com.lifesense.android.health.service.devicedetails.model.WeightUnit;
import com.lifesense.android.health.service.util.ToastUtil;

import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Create by qwerty
 * Create on 2020/6/9
 * 单位设置
 **/
public class DeviceUnitSettingActivity extends BaseActivity implements UnitChoiceRvAdapter.OnChoiceUnitListener {
    private static final String DEVICE_ID_EXTRA = "mac";
    private RecyclerView rvUnitChoice;
    private UnitChoiceRvAdapter unitChoiceRvAdapter;
    private String mac;
    public static final String CHOICE_UNIT_RES = "choice_unit_res";
    private int[] unitRes = new int[2];

    public static Intent makeIntent(Context context, String mac) {
        return new Intent(context, DeviceUnitSettingActivity.class).putExtra(DEVICE_ID_EXTRA, mac);
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_unit_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(getString(R.string.scale_unit));
        rvUnitChoice = findViewById(R.id.rv_unit_choice);
        unitChoiceRvAdapter = new UnitChoiceRvAdapter();
        unitChoiceRvAdapter.bindView(rvUnitChoice);
        unitChoiceRvAdapter.setOnChoiceUnitListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mac = getIntent().getStringExtra(DEVICE_ID_EXTRA);

        int netUnitType = Stream.of((List<WeightUnitConfig>) BleDeviceManager.getDefaultManager().getConfigs(mac, WeightUnitConfig.class)).findFirst().orElse(defaultWeightUnit()).getUnitType().getCommand();
        unitChoiceRvAdapter.setChoiceWeightUnit(WeightUnit.getUnitByNetUnitType(netUnitType));
        unitRes[0] = netUnitType;

    }


    private WeightUnitConfig defaultWeightUnit() {
        WeightUnitConfig weightUnitConfig = new WeightUnitConfig();
        weightUnitConfig.setUnitType(WeightUnitConfig.UnitType.UNIT_KG);
        return weightUnitConfig;
    }

    @Override
    public void onChoiceUnit(final Unit unit) {
        Log.d(getClass().getSimpleName(), "onChoiceUnit");
        showLoading();
        UnitCategory unitCategory = UnitCategory.getUnitTypeByUnit(unit);
        WeightUnitConfig weightUnitConfig = new WeightUnitConfig();
        weightUnitConfig.setUnitType(WeightUnitConfig.UnitType.fromCommand(((WeightUnit)unit).getUnitType().getCommand()));
        switch (unitCategory) {
            case UNIT_WEIGHT:
                BleDeviceManager.getDefaultManager().updateConfig(mac, weightUnitConfig, new Consumer<ConfigStatus>() {
                    @Override
                    public void accept(ConfigStatus configStatus) throws Exception {
                        if (configStatus == ConfigStatus.SUCCESS) {
                            dismissLoading();
                            Intent intent = new Intent();
                            unitRes[0] = ((WeightUnit) unit).getUnitType().getCommand();
                            intent.putExtra(CHOICE_UNIT_RES, unitRes[0]);
                            setResult(Activity.RESULT_OK, intent);
                        } else {
                            dismissLoading();
                            ToastUtil.showCenterShowToast(DeviceUnitSettingActivity.this, getString(R.string.scale_setting_fail_msg));
                        }
                    }


                });
                break;
            default:
                break;
        }

    }

    @Override
    protected boolean showNav() {
        return true;
    }


}
