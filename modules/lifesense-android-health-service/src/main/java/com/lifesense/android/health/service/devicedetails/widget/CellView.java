package com.lifesense.android.health.service.devicedetails.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.item.SettingItem;

/**
 * Create by qwerty
 * Create on 2020/11/3
 **/
public class CellView extends FrameLayout {
    private ViewDataBinding binding;
    private SettingItem settingItem;

    public CellView(@NonNull Context context) {
        this(context, null);
    }

    public CellView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CellView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.scale_item_cell,this,true);
    }

    public void setData(SettingItem settingItem) {
        this.settingItem = settingItem;
        binding.setVariable(BR.item, settingItem);
    }

    public SettingItem getData() {
       return settingItem;
    }

}
