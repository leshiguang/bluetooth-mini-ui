package com.lifesense.android.health.service.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.lifesense.android.health.service.R;

import java.lang.reflect.Method;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int width, int height, int layou) {

        super(context, R.style.scale_Theme_dialog);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private CustomDialog(Context context, int width, int height, int layou,
                         int gravity) {

        super(context, R.style.scale_Theme_dialog);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int layou) {

        super(context, R.style.scale_Theme_dialog);

//        // Full Screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//// No Titlebar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
    public CustomDialog(Context context, int layou, boolean isFull) {

        super(context, R.style.scale_Theme_dialog);

//        // Full Screen
        if (isFull) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    public static CustomDialog createBigDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);
        int h = (int) (dm.heightPixels * 0.55);

        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createStandardDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);

        int h = (int) (dm.heightPixels * 0.55);
        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createStandardDialogWrapContent(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);
        return new CustomDialog(context, w, LayoutParams.WRAP_CONTENT, layou);
    }

    public static CustomDialog createMicroDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.70);
        int h = (int) (dm.heightPixels * 0.40);

        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createMenuDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = dm.widthPixels;
        int h = LayoutParams.WRAP_CONTENT;

        return new CustomDialog(context, w, h, layou, Gravity.BOTTOM);
    }

    public static int getDpi(AppCompatActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        int height = 0;
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static int[] getScreenWH(Context poCotext) {
        WindowManager wm = (WindowManager) poCotext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[]{width, height};
    }

    public static int getVrtualBtnHeight(Context poCotext) {
        int[] location = getScreenWH(poCotext);
        int realHeiht = getDpi((AppCompatActivity) poCotext);
        int virvalHeight = realHeiht - location[1];
        return virvalHeight;
    }

    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    private double getw(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    private double geth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    private double getScreenSize(Context context) {
        double dps = Math.sqrt(Math.pow(getw(context), 2)
                + Math.pow(geth(context), 2));
        return dps / (160 * getDensity(context));
    }

    public void unCancelShow() {
        try {
            this.setCancelable(true);
            this.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelDismiss() {
        if (isShowing()) {
            try {
                this.setCancelable(true);
                this.dismiss();
            } catch (Exception e) {
            }
        }
    }

    /**
     * avoid not attached to window manager
     */
    @Override
    public void dismiss() {
        try {
            if (isShowing()) {
                super.dismiss();
            }
        } catch (Exception e) {
        }
    }
}

