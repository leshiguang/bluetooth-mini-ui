package com.lifesense.android.health.service.common.databinding.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class ViewAdapter {
    @BindingAdapter("navTitle")
    public static void setTitle(View view,String title) {
        Context context = view.getContext();
        if(context instanceof FragmentActivity) {
            ((FragmentActivity)context).setTitle(title);
        }
    }

    @BindingAdapter("navTitle")
    public static void setTitle(View view,int resId) {
        Context context = view.getContext();
        if(context instanceof FragmentActivity) {
            ((FragmentActivity)context).setTitle(context.getString(resId));
        }
    }

}
