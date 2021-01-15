package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;


import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.MessageReminderSettingActivity;

/**
 * Create by qwerty
 * Create on 2020/10/27
 * 消息提醒
 *
 * @author alexwu*/
public class MessageReminderItem extends SettingItem {

    @Override
    public String getTitle() {
        return context.getString(R.string.scale_message_reminder);
    }


    @Override
    public Class getTargetAction() {
        return MessageReminderSettingActivity.class;
    }


}
