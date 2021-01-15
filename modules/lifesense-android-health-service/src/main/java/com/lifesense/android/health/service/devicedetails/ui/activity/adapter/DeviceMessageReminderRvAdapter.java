package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.config.Call;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.common.ui.DefaultDataBindingViewHolder;
import com.lifesense.android.health.service.databinding.ScaleItemSwitchBinding;
import com.lifesense.android.health.service.util.ToastUtil;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2020/12/31
 **/
public class DeviceMessageReminderRvAdapter extends BaseDataBindingRvAdapter<ScaleItemSwitchBinding, Call> {
    private String mac;

    public DeviceMessageReminderRvAdapter(String mac) {
        this.mac = mac;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<ScaleItemSwitchBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getBinding().scCellSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Call call = items.get(position);
                if(isChecked != call.isEnable()) {
                    call.setEnable(isChecked);
                    BleDeviceManager.getDefaultManager().updateConfig(mac, call, new Consumer<ConfigStatus>() {
                        @Override
                        public void accept(ConfigStatus configStatus) throws Exception {
                            if (configStatus == ConfigStatus.SUCCESS) {
                                ToastUtil.showCenterShowToast(context, isChecked ? "提醒已经打开" : "提醒已经关闭");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_switch;
    }
}
