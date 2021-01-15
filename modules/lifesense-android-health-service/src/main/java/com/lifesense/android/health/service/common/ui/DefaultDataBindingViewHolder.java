package com.lifesense.android.health.service.common.ui;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by qwerty
 * Create on 2020/12/29
 **/
public class DefaultDataBindingViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    VB binding;

    public DefaultDataBindingViewHolder(@NonNull View itemView, VB binding) {
        super(itemView);
        this.binding = binding;
    }

    public VB getBinding() {
        return binding;
    }
}
