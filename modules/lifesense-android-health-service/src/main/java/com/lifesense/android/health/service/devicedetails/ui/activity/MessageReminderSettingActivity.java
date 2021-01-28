package com.lifesense.android.health.service.devicedetails.ui.activity;

import android.os.Bundle;
import com.lifesense.android.ble.core.application.model.config.Call;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.MessageReminderViewModel;

/**
 * Create by qwerty
 * Create on 2020/6/9
 * 消息提醒
 **/
public class MessageReminderSettingActivity extends BaseSettingActivity<MessageReminderViewModel,Call> {

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_pedometer_message_reminder;
    }

    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }
}
