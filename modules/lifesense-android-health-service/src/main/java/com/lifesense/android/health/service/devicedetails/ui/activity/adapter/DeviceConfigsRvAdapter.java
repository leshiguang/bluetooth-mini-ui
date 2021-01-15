package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import android.view.View;

import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.databinding.ScaleItemCellBinding;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

/**
 * Create by qwerty
 * Create on 2020/10/12
 **/
public class DeviceConfigsRvAdapter extends BaseDataBindingRvAdapter<ScaleItemCellBinding,SettingItem>  {
    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_cell;
    }

    @Override
    public void onItemClick(View view, int poi) {
        super.onItemClick(view, poi);
        items.get(poi).onClick(view);
    }
}
