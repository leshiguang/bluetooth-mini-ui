package com.lifesense.android.health.service.devicedetails.model;

import android.content.Context;


import com.lifesense.android.health.service.R;

import java.util.Arrays;
import java.util.List;

public enum UnitCategory {
    UNIT_WEIGHT(Arrays.asList(WeightUnit.UNIT_KG, WeightUnit.UNIT_LB), R.string.scale_weight_unit),
    UNIT_HEIGHT(Arrays.asList(HeightUnit.UNIT_CM, HeightUnit.UNIT_IN), R.string.scale_height_unit);
    private List<? extends Unit> units;
    private int resId;
    UnitCategory(List<? extends Unit> units, int resId) {
        this.units = units;
        this.resId = resId;
    }
    public List<? extends  Unit> getUnits() {
        return units;
    }

    public String getTypeString(Context context) {
        return context.getString(resId);
    }

    public int getUnitCounts() {
        return units.size();
    }

    public static UnitCategory getUnitTypeByUnit(Unit unit) {
        for (UnitCategory u:values()) {
            if(u.units.contains(unit)) {
                return u;
            }
        }
        return UnitCategory.UNIT_HEIGHT;
    }
}
