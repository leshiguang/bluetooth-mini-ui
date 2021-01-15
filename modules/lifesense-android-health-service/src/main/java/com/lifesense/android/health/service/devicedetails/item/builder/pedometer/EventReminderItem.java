package com.lifesense.android.health.service.devicedetails.item.builder.pedometer;

import com.lifesense.android.ble.core.application.model.config.EventReminder;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.ui.activity.EventReminderListActivity;

/**
 * Create by qwerty
 * Create on 2020/10/28
 * 事件提醒
 *
 * @author alexwu*/
public class EventReminderItem extends SettingItem<EventReminder> {


    @Override
    public String getTitle() {
        return "事件提醒";
    }

    @Override
    public Class getTargetAction() {
        return EventReminderListActivity.class;
    }


}
