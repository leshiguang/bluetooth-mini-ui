package com.lifesense.android.health.service.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lifesense.android.health.service.R;


/**
 * Created by Administrator on 2014/11/3.
 */
public class ToastUtil {


    public static void showLongToast(Context context, String content) {
        if (context != null) {
            showCustomCenterShowToast(context,content);
        }
    }

    public static void showCenterShowToast(Context context, String str) {
        showCustomCenterShowToast(context, str);
    }

    public static void showCustomCenterShowToast(Context mContext, int resId) {
        showCustomCenterShowToast(mContext,mContext.getResources().getString(resId));
    }

    public static void showCustomCenterShowToast(Context mContext, String info) {
        if (info == null || mContext == null) {
            return;
        }
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.scale_toast_group_tips, null);
        TextView textView = view.findViewById(R.id.td_content_tv);

        textView.setText(info);
        toast.setView(view);
        toast.show();
    }



}
