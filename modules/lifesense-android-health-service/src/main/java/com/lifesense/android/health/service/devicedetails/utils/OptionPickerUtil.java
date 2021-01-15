package com.lifesense.android.health.service.devicedetails.utils;

import android.content.Context;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2021/1/4
 **/
public class OptionPickerUtil {

    public static void showTimePicker(Context context, OnTimePickListener listener) {
        showRangePicker(context, 0, 24, 0, 60, (start, end) -> {
            if (listener != null) {
                listener.onTimePick(start, end);
            }
        });
    }
    public static void showRangePicker(Context context, int startMin, int startMax, int endMin, int endMax,OnRangePickListener listener) {
        List<Integer> startList = getIntegerRangeList(startMin, startMax);
        List<Integer> endList = getIntegerRangeList(endMin, endMax);

        OptionsPickerView timePickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            int start = startList.get(options1);
            int end = endList.get(options2);
            if (listener != null) {
                listener.onRangePick(start, end);
            }
        }).build();
        timePickerView.setNPicker(startList, endList, null);
        timePickerView.show();
    }

    public static void showNumberPicker(Context context, int start, int end, int space,OnNumSelectListener onNumSelectListener) {
        List<Integer> numberList = new ArrayList<>();
        for (int i = start; i <= end; i+=space) {
            numberList.add(i);
        }
        OptionsPickerView pickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            int num = numberList.get(options1);
            if (onNumSelectListener != null) {
                onNumSelectListener.onNumSelect(num);
            }
        }).build();
        pickerView.setPicker(numberList);
        pickerView.show();
    }

    public static void showNumberPicker(Context context, int start, int end, OnNumSelectListener onNumSelectListener) {
        showNumberPicker(context, start,end,1,onNumSelectListener);
    }

    private static List<Integer> getIntegerRangeList(int start, int end) {
        List<Integer> hourList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            hourList.add(i);
        }
        return hourList;
    }

    public interface OnRangePickListener {
        void onRangePick(int start, int end);
    }

    public interface OnNumSelectListener {
        void onNumSelect(int num);
    }

    public interface OnTimePickListener {
        void onTimePick(int hour, int min);
    }
}
