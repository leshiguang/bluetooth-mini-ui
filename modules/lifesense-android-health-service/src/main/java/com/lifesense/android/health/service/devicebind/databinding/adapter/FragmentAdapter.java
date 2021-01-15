package com.lifesense.android.health.service.devicebind.databinding.adapter;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class FragmentAdapter {
    @BindingAdapter("content")
    public static void start(FrameLayout frameLayout, LiveData<Fragment> fragment) {
        Context context = frameLayout.getContext();
        if (context instanceof FragmentActivity) {
            fragment.observe(((FragmentActivity) context), new Observer<Fragment>() {
                @Override
                public void onChanged(Fragment fragment) {
                    FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (fragment != null) {
                        transaction.replace(frameLayout.getId(), fragment);
                        transaction.commitAllowingStateLoss();
                    }
                }
            });
        }
    }

    @BindingAdapter("title")
    public static void title(FrameLayout frameLayout, LiveData<String> title) {
        Context context = frameLayout.getContext();
        if (context instanceof FragmentActivity) {
            title.observe(((FragmentActivity) context), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    ((FragmentActivity)context).setTitle(s);
                }
            });
            ((FragmentActivity)context).setTitle(title.getValue());
        }

    }
}
