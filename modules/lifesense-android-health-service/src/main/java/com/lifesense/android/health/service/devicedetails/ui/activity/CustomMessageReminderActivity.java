package com.lifesense.android.health.service.devicedetails.ui.activity;



import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingActivity;
import com.lifesense.android.health.service.devicedetails.ui.activity.vm.CustomMessageReminderViewModel;

/**
 * Create by qwerty
 * Create on 2021/1/21
 **/
public class CustomMessageReminderActivity extends BaseDataBindingActivity<CustomMessageReminderViewModel> {
    @Override
    public int getViewModelVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.scale_activity_custom_message_reminder;
    }
}
