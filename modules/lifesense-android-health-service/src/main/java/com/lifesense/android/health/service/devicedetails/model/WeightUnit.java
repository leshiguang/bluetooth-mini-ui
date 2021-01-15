package com.lifesense.android.health.service.devicedetails.model;

import android.content.Context;

import androidx.annotation.StringRes;

import com.lifesense.android.ble.core.application.model.config.WeightUnitConfig;
import com.lifesense.android.health.service.R;


/**
 * Create by qwerty
 * Create on 2020/6/10
 **/
public enum WeightUnit implements Unit{
    UNIT_KG(R.string.scale_unit_kg, WeightUnitConfig.UnitType.UNIT_KG),
    UNIT_LB(R.string.scale_unit_lb, WeightUnitConfig.UnitType.UNIT_LB);
    private int unitNameRes;
    private WeightUnitConfig.UnitType unitType;

    WeightUnit(@StringRes int unitNameRes , WeightUnitConfig.UnitType unitType) {
        this.unitNameRes = unitNameRes;
        this.unitType = unitType;
    }

    @Override
    public String getUnitName(Context context) {
        if(unitNameRes == 0) {
            return "";
        }
        return context.getString(unitNameRes);
    }

    public WeightUnitConfig.UnitType getUnitType() {
        return unitType;
    }

    public static WeightUnit getUnitByNetUnit(WeightUnitConfig.UnitType netUnitType) {
        for (WeightUnit weightUnit : values()) {
            if(weightUnit.unitType == netUnitType) {
                return weightUnit;
            }
        }
        return UNIT_KG;
    }

    public static WeightUnit getUnitByNetUnitType(int netUnitType) {
        for (WeightUnit weightUnit : values()) {
            if (weightUnit.unitType.getCommand() == netUnitType) {
                return weightUnit;
            }
        }
        return UNIT_KG;
    }
}
