package com.lifesense.android.health.service.devicebind.adapter;

import androidx.annotation.NonNull;

import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.DefaultDataBindingViewHolder;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.common.ui.SingleChoiceDataBindingRvAdapter;
import com.lifesense.android.health.service.databinding.ScaleListviewFindDeviceResultItemBinding;

/**
 * Create by qwerty
 * Create on 2020/12/30
 **/
public class FindDeviceResultRvAdapter extends SingleChoiceDataBindingRvAdapter<ScaleListviewFindDeviceResultItemBinding, LSEDeviceInfoApp> {
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
        return R.layout.scale_listview_find_device_result_item;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<ScaleListviewFindDeviceResultItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == 0) {
            holder.getBinding().setNearest(true);
        } else {
            holder.getBinding().setNearest(false);
        }
    }
}
