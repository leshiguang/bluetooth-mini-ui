package com.lifesense.android.health.service.devicedetails.model;

import android.content.Context;

import androidx.annotation.StringRes;

import com.lifesense.android.health.service.R;

public enum HeightUnit implements Unit{
    UNIT_CM(R.string.scale_unit_cm, NetHeightUnitType.CM),
    UNIT_IN(R.string.scale_unit_in, NetHeightUnitType.IN);
    private int unitNameRes;
    private NetHeightUnitType unitType;

    HeightUnit(@StringRes int unitNameRes , NetHeightUnitType unitType) {
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

    public NetHeightUnitType getUnitType() {
        return unitType;
    }

    public static HeightUnit getUnitByNetUnit(NetHeightUnitType netWeightUnitType) {
        for (HeightUnit heightUnit : values()) {
            if(heightUnit.unitType == netWeightUnitType) {
                return heightUnit;
            }
        }
        return UNIT_CM;
    }

    public static HeightUnit getUnitByNetUnitType(int netUnitType) {
        for (HeightUnit heightUnit : values()) {
            if (heightUnit.unitType.getNetUnitTypeCommand() == netUnitType) {
                return heightUnit;
            }
        }
        return UNIT_CM;
    }
}
