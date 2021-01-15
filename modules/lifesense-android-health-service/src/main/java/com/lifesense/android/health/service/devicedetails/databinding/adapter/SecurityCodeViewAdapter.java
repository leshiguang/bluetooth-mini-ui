package com.lifesense.android.health.service.devicedetails.databinding.adapter;

import androidx.databinding.BindingAdapter;

import com.lifesense.android.health.service.devicebind.widget.SecurityCodeView;

/**
 * Create by qwerty
 * Create on 2020/12/30
 **/
public class SecurityCodeViewAdapter {
    @BindingAdapter("count")
    public static void setCount(SecurityCodeView securityCodeView, int count) {
        securityCodeView.setDefaultCount(count);
    }
}
