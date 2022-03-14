package com.lifesense.android.health.service.devicedetails.ui.activity;

import com.lifesense.android.ble.device.band.model.config.EventReminder;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.EventReminderDetailViewModel;
/**
 * Create by qwerty
 * Create on 2020/6/9
 * 事件提醒设置
 **/
public class EventReminderDetailActivity extends BaseSettingActivity<EventReminderDetailViewModel, EventReminder> {

    public static final String DATA_EXTRA = "data";

    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_pedometer_event_reminder;
    }
}
