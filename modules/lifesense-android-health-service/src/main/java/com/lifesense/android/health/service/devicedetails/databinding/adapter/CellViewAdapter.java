package com.lifesense.android.health.service.devicedetails.databinding.adapter;

import android.widget.CompoundButton;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.lifesense.android.health.service.devicedetails.item.SettingItem;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.devicedetails.widget.CellView;

/**
 * Create by qwerty
 * Create on 2021/1/6
 **/
public class CellViewAdapter {
    @BindingAdapter("title")
    public static void setTitle(CellView cellView, String title) {
        SettingItem settingItem = cellView.getData();
        if (settingItem == null) {
            settingItem = new SettingItem() {
                @Override
                public Class getTargetAction() {
                    return null;
                }
            };
        }
        settingItem.setTitle(title);
        cellView.setData(settingItem);
    }

    @BindingAdapter("value")
    public static void setValue(CellView cellView, String value) {
        SettingItem settingItem = cellView.getData();
        if (settingItem == null) {
            settingItem = new SettingItem() {
                @Override
                public Class getTargetAction() {
                    return null;
                }
            };
        }
        settingItem.setValue(value);
        cellView.setData(settingItem);
    }

    @BindingAdapter("value")
    public static void setValue(CellView cellView, LiveData<String> value) {
        SettingItem settingItem = cellView.getData();
        if (settingItem == null) {
            settingItem = new SettingItem() {
                @Override
                public Class getTargetAction() {
                    return null;
                }
            };
        }
        settingItem.setValue(value.getValue());
        cellView.setData(settingItem);
        if (cellView.getContext() instanceof FragmentActivity && value != null) {
            value.observe((FragmentActivity) cellView.getContext(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    SettingItem settingItem = cellView.getData();
                    if (settingItem == null) {
                        settingItem = new SettingItem() {
                            @Override
                            public Class getTargetAction() {
                                return null;
                            }
                        };
                    }
                    settingItem.setValue(s);
                    cellView.setData(settingItem);
                }
            });
        }
    }

    @BindingAdapter("cellType")
    public static void setCellType(CellView cellView, ItemType itemType) {
        SettingItem settingItem = cellView.getData();
        if (settingItem == null) {
            settingItem = new SettingItem() {
                @Override
                public Class getTargetAction() {
                    return null;
                }
            };
        }
        settingItem.setItemType(itemType);
        cellView.setData(settingItem);
    }

    @BindingAdapter("openSwitch")
    public static void setOpenSwitch(CellView cellView, boolean open) {
        SettingItem settingItem = cellView.getData();
        if (settingItem == null) {
            settingItem = new SettingItem() {
                @Override
                public Class getTargetAction() {
                    return null;
                }
            };
        }
        settingItem.setOpenSwitch(open);
        cellView.setData(settingItem);
    }

    @BindingAdapter("android:onCheckedChanged")
    public static void setOnCheckedChangeListener(CellView cellView, CompoundButton.OnCheckedChangeListener listener) {
        SettingItem oldSettingItem = cellView.getData();
        SettingItem settingItem = new SettingItem() {
            @Override
            public Class getTargetAction() {
                return null;
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                super.onCheckedChanged(buttonView, isChecked);
                if (listener != null) {
                    listener.onCheckedChanged(buttonView, isChecked);

                }
            }
        };
        if (oldSettingItem != null) {
            settingItem.setTitle(oldSettingItem.getTitle());
            settingItem.setValue(oldSettingItem.getValue());
            settingItem.setOpenSwitch(oldSettingItem.isOpenSwitch());
            settingItem.setItemType(oldSettingItem.getItemType());
        }
        cellView.setData(settingItem);
    }


}
