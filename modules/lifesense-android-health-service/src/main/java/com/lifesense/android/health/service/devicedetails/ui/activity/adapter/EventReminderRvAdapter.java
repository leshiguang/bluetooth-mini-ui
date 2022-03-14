package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.lifesense.android.ble.device.band.model.config.EventReminder;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.common.ui.DefaultDataBindingViewHolder;
import com.lifesense.android.health.service.databinding.ScaleItemPedometerEventReminderListBinding;

/**
 * Create by qwerty
 * Create on 2020/11/3
 **/
public class EventReminderRvAdapter extends BaseDataBindingRvAdapter<ScaleItemPedometerEventReminderListBinding, EventReminder> {
    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_pedometer_event_reminder_list;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<ScaleItemPedometerEventReminderListBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getBinding().ivDelEventReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick((ImageView) v, position);
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

    OnImageClickListener onImageClickListener;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnImageClickListener {
        void onImageClick(ImageView imageView, int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }
}
