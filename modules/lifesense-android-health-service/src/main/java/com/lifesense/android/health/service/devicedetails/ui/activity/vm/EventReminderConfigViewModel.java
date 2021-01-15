package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import com.lifesense.android.ble.core.application.model.config.EventReminder;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.devicedetails.ui.activity.EventReminderDetailActivity;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.EventReminderRvAdapter;
import com.lifesense.android.health.service.devicedetails.widget.AlertDialogFragment;
import com.lifesense.android.health.service.util.ToastUtil;

import java.util.List;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class EventReminderConfigViewModel extends ConfigViewModel<EventReminder>{
    MutableLiveData<BaseDataBindingRvAdapter> adapter = new MutableLiveData<>();
    private AlertDialogFragment dialogFragment;
    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        EventReminderRvAdapter adapter = new EventReminderRvAdapter();
        adapter.setItems(getConfigs().getValue());
        adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(context, EventReminderDetailActivity.class);
            intent.putExtra("deviceInfo", getDeviceInfo().getValue());
            intent.putExtra(EventReminderDetailActivity.DATA_EXTRA,adapter.getItem(position));
            context.startActivity(intent);
        });
        adapter.setOnImageClickListener((imageView, position) -> showDelEventReminderDialog(imageView.getContext(), position));
        getConfigs().observe(context, eventReminders -> adapter.setItems(eventReminders));
        getConfigStatus().observe(context, configStatus -> {
            if(configStatus == ConfigStatus.SUCCESS) {
                fetchConfigs();
            }
        });
        this.adapter.setValue(adapter);
    }



    @Override
    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return adapter;
    }

    public void onAddClick(View v) {
        if (adapter.getValue().getItemCount() >= 5) {
            ToastUtil.showCenterShowToast(v.getContext(), "最多设置5个提醒");
            return;
        }
        Intent intent = new Intent(v.getContext(), EventReminderDetailActivity.class);
        intent.putExtra("deviceInfo", getDeviceInfo().getValue());
        v.getContext().startActivity(intent);
    }

    private void showDelEventReminderDialog(Context context, int position) {
        dialogFragment = new AlertDialogFragment.Builder()
                .setTitle("提示")
                .setMsg("确认删除该提醒？")
                .setPositiveClickListener("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFragment.dismiss();
                        List<EventReminder> reminders = getConfigs().getValue();
                        EventReminder reminder = reminders.get(position);
                        reminder.setEnable(false);
                        deleteConfig(reminder);
                    }
                })
                .build();
        if(context instanceof FragmentActivity) {
            dialogFragment.show(((FragmentActivity)context).getSupportFragmentManager());
        }
    }
}
