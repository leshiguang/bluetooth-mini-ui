package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.core.application.model.config.EventReminder;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.EventReminderConfigViewModel;
/**
 * Create by qwerty
 * Create on 2020/11/3
 * 事件提醒列表
 *
 * @author alexwu*/
public class EventReminderListActivity extends BaseSettingActivity<EventReminderConfigViewModel,EventReminder>{

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_pedometer_event_reminder_list;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchConfigs();
    }
}
