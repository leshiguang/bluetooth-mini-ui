package com.lifesense.android.health.service.devicedetails.databinding.adapter;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;

/**
 * Create by qwerty
 * Create on 2021/1/6
 **/
public class RecyclerViewAdapter {

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, LiveData<BaseDataBindingRvAdapter> adapter) {
        Context context = recyclerView.getContext();
        if (context instanceof FragmentActivity) {
            adapter.observe(((FragmentActivity) context), new Observer<BaseDataBindingRvAdapter>() {
                @Override
                public void onChanged(BaseDataBindingRvAdapter baseDataBindingRvAdapter) {
                    recyclerView.setAdapter(baseDataBindingRvAdapter);
                }
            });
            recyclerView.setAdapter(adapter.getValue());
        }
    }
}
