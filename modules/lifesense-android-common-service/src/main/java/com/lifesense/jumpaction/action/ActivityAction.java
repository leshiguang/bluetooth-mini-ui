package com.lifesense.jumpaction.action;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.lifesense.jumpaction.bean.LsBundle;
import com.lifesense.jumpaction.performer.activity.ActivityActionConstant;
import com.lifesense.jumpaction.performer.activity.ActivityPerformer;

import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by liuxinyi on 16/12/29.
 */

public class ActivityAction extends LSAction {
    public static final int TYPE_ACTION_ID = 1;
    public static final int TYPE_CLASS = 2;


    private Context mContext;
    private LsBundle mLsBundle;
    private Bundle mBundle;
    private Class<?> mActivityClass;
    private int mType = TYPE_ACTION_ID;

    public Context getContext() {
        return mContext;
    }

    private int flags;

    public int getFlags() {
        return flags;
    }

    public ActivityAction setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public ActivityAction(String actionId, Context context) {
        newBundle();
        setActionId(actionId);
        mType = TYPE_ACTION_ID;
        mContext = context;

    }

    public int getType() {
        return mType;
    }

    public ActivityAction(Class<?> activityCla, Context context) {
        newBundle();
        mActivityClass = activityCla;
        mType = TYPE_CLASS;
        mContext = context;
    }

    public Class<?> getActivityClass() {
        return mActivityClass;
    }

    public LsBundle getLsBundle() {
        return mLsBundle;
    }

    public Bundle getBundle() {
        mBundle.putParcelable(ActivityActionConstant.ACTION_DATA, getLsBundle());
        return mBundle;
    }

    public ActivityAction putInt(String key, int value) {
        mLsBundle.put(key, value);
        return this;
    }

    public ActivityAction putFloat(String key, float value) {
        mLsBundle.put(key, value);
        return this;
    }

    public ActivityAction putDouble(String key, double value) {
        mLsBundle.put(key, value);
        return this;
    }

    public ActivityAction putLong(String key, long value) {
        mLsBundle.put(key, value);
        return this;
    }

    public ActivityAction putString(String key, String value) {
        mLsBundle.put(key, value);
        return this;
    }

    public ActivityAction putParcelable(String key, Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public ActivityAction putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public ActivityAction put(String key, Object object) {
        mLsBundle.put(key, object);
        return this;
    }

    public ActivityAction putJson(JSONObject dataJson) {
        mLsBundle.putAll(dataJson);
        return this;
    }

    private synchronized void newBundle() {
        if (mLsBundle == null) {
            mLsBundle = new LsBundle();
        }
        if (mBundle == null) {
            mBundle = new Bundle();
        }
    }

    @Override
    public String getActionType() {
        return ActivityPerformer.class.getName();
    }
}
