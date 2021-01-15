package com.lifesense.android.health.service.deviceconfig.adapter;
import android.content.Intent;
import android.view.View;


import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.databinding.ScaleItemDeviceStatusListBinding;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.device.DeviceStateWrapper;
import com.lifesense.android.health.service.util.DeviceUtils;

/**
 * Author:  winds
 * Email:   heardown@163.com
 * Date:    2019/9/12.
 * Desc:    手表和NB设备的判断 暂时根据设备型号判断   如若新接入需要主动补全判断的逻辑
 */
public class DeviceStatusListAdapter extends BaseDataBindingRvAdapter<ScaleItemDeviceStatusListBinding,DeviceStateWrapper> {
    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_device_status_list;
    }

    @Override
    public void onItemClick(View view, int poi) {
        super.onItemClick(view, poi);
        Intent intent = DeviceUtils.getDeviceSettingsIntent(context,items.get(poi).getDevice().getMac());
        context.startActivity(intent);
    }
}
