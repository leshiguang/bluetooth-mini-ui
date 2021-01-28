package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.config.Call;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.common.ui.DefaultDataBindingViewHolder;
import com.lifesense.android.health.service.databinding.ScaleItemSwitchBinding;
import com.lifesense.android.health.service.devicedetails.ui.activity.CustomMessageReminderActivity;
import com.lifesense.android.health.service.util.ToastUtil;

import java.util.List;

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
        holder.getBinding().scCellSwitch.setVisibility(position == getItemCount() - 1 ? View.GONE : View.VISIBLE);
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
    public void onItemClick(View view, int poi) {
        super.onItemClick(view, poi);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, poi);
        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void setItems(List<Call> items) {
        Call call = new Call();
        items.add(call);
        super.setItems(items);
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
