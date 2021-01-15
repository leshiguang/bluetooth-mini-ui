package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import com.lifesense.android.ble.core.application.model.config.DialPlate;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.SingleChoiceDataBindingRvAdapter;
import com.lifesense.android.health.service.databinding.ScaleItemPedometerDialPeaceBinding;

/**
 * Create by qwerty
 * Create on 2021/1/4
 **/
public class DialPlateRvAdapter extends SingleChoiceDataBindingRvAdapter<ScaleItemPedometerDialPeaceBinding, DialPlate.DialPlateType> {
    @Override
    public int getSelectedVariableId() {
        return BR.selected;
    }

    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_pedometer_dial_peace;
    }
}
