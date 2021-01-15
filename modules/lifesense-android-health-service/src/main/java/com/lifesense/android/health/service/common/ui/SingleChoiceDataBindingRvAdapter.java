package com.lifesense.android.health.service.common.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.lifesense.android.health.service.BR;

/**
 * Create by qwerty
 * Create on 2021/1/4
 **/
abstract public class SingleChoiceDataBindingRvAdapter<VB extends ViewDataBinding, T> extends BaseDataBindingRvAdapter<VB, T> {
    private int selectedPos;
    private OnSelectListener selectListener;

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<VB> holder, int position) {
        super.onBindViewHolder(holder, position);
        if(selectedPos == position) {
            holder.getBinding().setVariable(getSelectedVariableId(), true);
        } else {
            holder.getBinding().setVariable(getSelectedVariableId(),false);
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        super.onItemClick(view, pos);
        if (selectedPos != pos) {
            selectedPos = pos;
            notifyDataSetChanged();
            onSelectChanged(view, pos);
        }
    }

    public void onSelectChanged(View view, int pos) {
        if(selectListener != null) {
            selectListener.onSelectChanged(view, pos);
        }
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    public void setSelectedItem(T item) {
        setSelectedPos(items.indexOf(item));
    }

    public T getSelectedItem() {
        if (items == null) {
            return null;
        }

        return items.get(selectedPos);
    }

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public interface OnSelectListener {
        void onSelectChanged(View view, int pos);
    }

    abstract public int getSelectedVariableId();
}
