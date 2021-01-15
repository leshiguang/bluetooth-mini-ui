package com.lifesense.jumpaction.action;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by liuxinyi on 2017/4/18.
 */

public class ActivityActionForResult extends ActivityAction {
    private int mRequestCode;

    public ActivityActionForResult(String actionId, AppCompatActivity context, int requestCode) {
        super(actionId, context);
        mRequestCode = requestCode;
    }

    public ActivityActionForResult(Class<?> actionClasss, AppCompatActivity context, int requestCode) {
        super(actionClasss, context);
        mRequestCode = requestCode;
    }


    public int getRequestCode() {
        return mRequestCode;
    }

    public ActivityActionForResult setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }
}
