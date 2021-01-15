package com.lifesense.android.health.service.devicebind.databinding.adapter;

import androidx.databinding.BindingAdapter;

import com.lifesense.android.health.service.devicebind.widget.RippleBackground;

/**
 * Create by qwerty
 * Create on 2020/12/30
 **/
public class RippleBackgroundAdapter {
    @BindingAdapter("start")
    public static void start(RippleBackground rippleBackground, boolean start) {
        if(start) {
            rippleBackground.startRippleAnimation();
        } else {
            rippleBackground.stopRippleAnimation();
        }
    }
}
