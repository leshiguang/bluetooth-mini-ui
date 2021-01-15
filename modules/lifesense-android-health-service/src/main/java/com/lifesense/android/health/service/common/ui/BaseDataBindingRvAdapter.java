package com.lifesense.android.health.service.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Create by qwerty
 * Create on 2020/12/29
 **/
public abstract class BaseDataBindingRvAdapter<VB extends ViewDataBinding, T> extends RecyclerView.Adapter<DefaultDataBindingViewHolder<VB>> {
    protected Context context;
    protected List<T> items;
    @NonNull
    @Override
    public DefaultDataBindingViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        VB binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new DefaultDataBindingViewHolder<VB>(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<VB> holder, int position) {
        T item = items.get(position);
        holder.getBinding().setVariable(getItemVariableId(),item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    abstract public @LayoutRes int  getItemLayoutId(int poi);

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }



    public Context getContext() {
        return context;
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int pos) {
        return items.get(pos);
    }
    public void onItemClick(View view, int poi) {

    }

    public abstract int getItemVariableId();
}
