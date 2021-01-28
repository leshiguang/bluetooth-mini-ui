package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lifesense.android.ble.core.application.model.config.EventReminder;
import com.lifesense.android.ble.core.application.model.enums.Day;
import com.lifesense.android.ble.core.application.model.enums.VibrationMode;
import com.lifesense.android.health.service.devicedetails.utils.OptionPickerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lifesense.android.health.service.devicedetails.ui.activity.EventReminderDetailActivity.DATA_EXTRA;
import static com.lifesense.android.health.service.devicedetails.utils.DataTransformUtil.getModeText;
import static com.lifesense.android.health.service.devicedetails.utils.DataTransformUtil.getWeekText;

/**
 * Create by qwerty
 * Create on 2021/1/6
 **/
public class EventReminderDetailViewModel extends ConfigViewModel<EventReminder> {
    private static final int MAX_INDEX = 5;

    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        EventReminder eventReminder = (EventReminder) context.getIntent().getSerializableExtra(DATA_EXTRA);
        if (eventReminder == null) {
            eventReminder = new EventReminder();
            int index = getEventRemindsAddIndex(getConfigs().getValue());
            eventReminder.setIndex(index);
        }
        this.setUpdateConfig(eventReminder);
        content.setValue(getUpdateConfig().getValue().getContent());
        content.observe(context, s -> getUpdateConfig().getValue().setContent(s));
    }

    LiveData<Boolean> enable = Transformations.map(getUpdateConfig(), input -> input.isEnable());

    LiveData<String> remindTime = Transformations.map(getUpdateConfig(), input -> input.getHour() + ":" + input.getMin());

    LiveData<String> remindRepeatTime = Transformations.map(getUpdateConfig(), input -> {
        List<Day> days = input.getRepeatDays();
        StringBuffer sb = new StringBuffer();
        if (days != null) {
            for (int i = 0; i < days.size(); i++) {
                String fruit = getWeekText(days.get(i));
                sb.append(fruit + " ");
            }
        }
        return sb.toString();
    });

    LiveData<String> vibrationMode = Transformations.map(getUpdateConfig(), input -> getModeText(input.getVibrationMode()));

    LiveData<String> vibrationTensity1 = Transformations.map(getUpdateConfig(), input -> String.valueOf(input.getVibrationLevel()));

    LiveData<String> vibrationTensity2 = Transformations.map(getUpdateConfig(), input -> String.valueOf(input.getVibrationLevel1()));

    LiveData<String> vibrationDuration = Transformations.map(getUpdateConfig(), input -> String.valueOf(input.getVibrationDuration()));

    MutableLiveData<String> content = new MutableLiveData<>();

    public LiveData<Boolean> getEnable() {
        return enable;
    }

    public LiveData<String> getRemindTime() {
        return remindTime;
    }

    public LiveData<String> getRemindRepeatTime() {
        return remindRepeatTime;
    }

    public LiveData<String> getVibrationMode() {
        return vibrationMode;
    }

    public LiveData<String> getVibrationTensity1() {
        return vibrationTensity1;
    }

    public LiveData<String> getVibrationTensity2() {
        return vibrationTensity2;
    }

    public LiveData<String> getVibrationDuration() {
        return vibrationDuration;
    }

    public MutableLiveData<String> getContent() {
        return content;
    }

    public void setContent(MutableLiveData<String> content) {
        this.content = content;
    }

    public void onAddClick(View view) {
        updateConfig();
    }

    public void onSwitchChanged(CompoundButton compoundButton, boolean isCheck) {
        EventReminder eventReminder = getUpdateConfig().getValue();
        eventReminder.setEnable(isCheck);
        if (eventReminder.isEnable() != isCheck) {
            setUpdateConfig(eventReminder);
        }
    }

    public void onReminderHourClick(View view) {
        showReminderHourPicker(view.getContext());
    }

    public void onReminderRepeatTimeClick(View view) {
        showWeekSelectDialog(view.getContext());
    }

    public void onReminderVibrationModeClick(View view) {
        showVibrationModePicker(view.getContext());
    }

    public void onReminderVibrationTensity1Click(View view) {
        showVibrationTensity1Picker(view.getContext());
    }

    public void onReminderVibrationTensity2Click(View view) {
        showVibrationTensity2Picker(view.getContext());
    }

    public void onReminderVibrationDurationClick(View view) {
        showVibrationDurationPicker(view.getContext());
    }

    private void showReminderHourPicker(Context context) {
        OptionPickerUtil.showTimePicker(context, (hour, min) -> {
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setHour((short) hour);
            eventReminder.setMin((short) min);
            setUpdateConfig(eventReminder);
        });

    }

    private void showVibrationModePicker(Context context) {
        List<String> mode = Arrays.asList("持续震动","间歇震动，震动强度不变","间歇震动，震动强度由小变大","间歇震动，震动强度由大变小","震动强度大小循环");
        OptionsPickerView pickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setVibrationMode(VibrationMode.values()[options1]);
            setUpdateConfig(eventReminder);
        }).build();
        pickerView.setPicker(mode);
        pickerView.show();
    }

    private void showVibrationTensity1Picker(Context context) {
        OptionPickerUtil.showNumberPicker(context, 0, 9, num -> {
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setVibrationLevel((short) num);
            setUpdateConfig(eventReminder);
        });
    }

    private void showVibrationTensity2Picker(Context context) {
        OptionPickerUtil.showNumberPicker(context, 0, 9, num -> {
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setVibrationLevel1((short) num);
            setUpdateConfig(eventReminder);
        });
    }

    private void showVibrationDurationPicker(Context context) {
        OptionPickerUtil.showNumberPicker(context, 1, 60, num -> {
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setVibrationDuration((short) num);
            setUpdateConfig(eventReminder);
        });
    }

    private void showWeekSelectDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请选择提醒的日期");
        final String items[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        List<Day> days = getUpdateConfig().getValue().getRepeatDays();
        final boolean[] checkedItems = new boolean[Day.values().length];
        for (int i = 0; i < Day.values().length; i++) {
            if (days != null && days.indexOf(Day.values()[i]) >= 0) {
                checkedItems[i] = true;
            }
        }
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton("确定", (dialog, which) -> {
            List<Day> selectedDays = new ArrayList<>();
            for (int i = 0; i < checkedItems.length; i++) {
                //判断一下是选中的
                if (checkedItems[i]) {
                    selectedDays.add(Day.values()[i]);
                }
            }
            EventReminder eventReminder = getUpdateConfig().getValue();
            eventReminder.setRepeatDays(selectedDays);
            setUpdateConfig(eventReminder);
            dialog.dismiss();
        });
        builder.show();
    }


    public int getEventRemindsAddIndex(List<EventReminder> cfgs) {
        if (cfgs == null || cfgs.isEmpty()) {
            // index 从1开始
            return 1;
        }
        if (cfgs.size() > MAX_INDEX) {
            return MAX_INDEX + 1;
        }
        boolean[] bs = new boolean[MAX_INDEX];
        for (EventReminder cfg : cfgs) {
            // error index:..
            if (cfg.getIndex() <= 0 || cfg.getIndex() > MAX_INDEX) {
                continue;
            }
            bs[cfg.getIndex() - 1] = true;
        }
        for (int i = 0; i < bs.length; i++) {
            if (!bs[i]) {
                return i + 1;
            }
        }
        // 异常状态，返回一个大于最大长度的值 表明没有可用的index
        return MAX_INDEX + 1;
    }

    @Override
    public String getTitleStr() {
        return "事件提醒设置";
    }
}
