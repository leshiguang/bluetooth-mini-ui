package com.lifesense.jumpaction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by liuxinyi on 2017/4/18.
 */

public class LsBundle implements Parcelable {
    private String mData;
    private JSONObject mDataJson;

    public LsBundle() {
        newJson();
    }

    public LsBundle(String data) {
        mData = data;
        newJson(mData);
    }

    private void newJson(String data) {
        try {
            mDataJson = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            mDataJson = new JSONObject();
        }
    }

    private synchronized void newJson() {
        if (mDataJson == null) {
            mDataJson = new JSONObject();
        }
    }

    public LsBundle(JSONObject jsonObject) {
        mDataJson = jsonObject;
        mData = jsonObject.toString();
    }

    public Object get(String key) {
        return mDataJson.opt(key);
    }

    public String getString(String key) {
        return mDataJson.optString(key);
    }

    public String getString(String key, String def) {
        return mDataJson.optString(key, def);
    }

    public double getDouble(String key) {
        return mDataJson.optDouble(key);
    }

    public double getDouble(String key, double def) {
        return mDataJson.optDouble(key, def);
    }

    public float getFloat(String key) {
        return (float) mDataJson.optDouble(key);
    }

    public float getFloat(String key, float fallback) {
        return (float) mDataJson.optDouble(key, fallback);
    }

    public long getLong(String key) {
        return mDataJson.optLong(key);
    }

    public long getLong(String key, long fallback) {
        return mDataJson.optLong(key, fallback);
    }

    public int getInt(String key) {
        return mDataJson.optInt(key);
    }

    public int getInt(String key, int fallback) {
        return mDataJson.optInt(key, fallback);
    }

    public boolean getBoolean(String key) {
        return mDataJson.optBoolean(key);
    }

    public boolean getBoolean(String key, boolean fallback) {
        return mDataJson.optBoolean(key, fallback);
    }


    public boolean isNull(String key) {
        return mDataJson.isNull(key);
    }

    public LsBundle put(String key, Object value) {
        try {
            mDataJson.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;

    }

    public JSONObject getDataJson() {
        return mDataJson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mDataJson != null) {
            mData = mDataJson.toString();
        }
        dest.writeString(this.mData);
    }

    protected LsBundle(Parcel in) {
        this.mData = in.readString();
        newJson(mData);
    }

    public void putAll(JSONObject jsonObject) {
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object obj = jsonObject.opt(key);
            try {
                mDataJson.put(key, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return mDataJson.toString();
    }

    public static final Creator<LsBundle> CREATOR = new Creator<LsBundle>() {
        @Override
        public LsBundle createFromParcel(Parcel source) {
            return new LsBundle(source);
        }

        @Override
        public LsBundle[] newArray(int size) {
            return new LsBundle[size];
        }
    };
}
