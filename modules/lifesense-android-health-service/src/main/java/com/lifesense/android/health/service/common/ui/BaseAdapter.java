package com.lifesense.android.health.service.common.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context mContext;
    protected List<T> mList;

    public BaseAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView);
    }


    public View getView(int position, View convertView) {
        return null;
    }

    @Override
    public int getCount() {
        if (null != mList) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != mList) {
            if (position < 0 || position >= mList.size()) {
                return null;
            }
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void appentToList(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
        list = null;
    }

    public void appentToList(T t) {
        mList.add(t);
        notifyDataSetChanged();
    }

    public void appentToListFirst(T t) {
        mList.add(0, t);
        notifyDataSetChanged();
    }

    public void appentToListFirst(List<T> list) {
        mList.addAll(0, list);
        notifyDataSetChanged();
        list = null;
    }

    /**
     * @param list
     */
    public void changeList(List<T> list) {
        mList = list == null ? new ArrayList<T>() : list;
        notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除列表中某一项
     */
    public void removeObject(T t) {
        mList.remove(t);
        notifyDataSetChanged();
        t = null;
    }

    public void updateObject(T t) {
        mList.set(mList.indexOf(t), t);
        notifyDataSetChanged();
    }
}
