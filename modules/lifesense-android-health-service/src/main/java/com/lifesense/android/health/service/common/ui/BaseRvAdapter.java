package com.lifesense.android.health.service.common.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mItems;
    private OnItemClickListener onItemClickListener;
    private OnClickListener onClickListener;

    public BaseRvAdapter() {
        mItems = new ArrayList<>();
        initListener();
    }
    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<T> items) {
        if (items != null) {
            this.mItems.clear();
            this.mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createHolderView(parent , viewType);
    }

    protected abstract RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mItems.size() <= position){
            return;
        }
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(onClickListener);
        bindHolderView(holder , mItems.get(position), position);
    }
    // 可以在这分发出去有header的
    protected abstract void bindHolderView(RecyclerView.ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<T> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }
    public void addItem(int position, T item) {
        if (item != null) {
            this.mItems.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.mItems.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            this.mItems.clear();
            addAll(items);
        }
    }

    public final void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    public T getItem(int pos) {
        return mItems.get(pos);
    }

    public List<T> getItems(){
        return this.mItems;
    }

    public void bindListView(RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }
}
