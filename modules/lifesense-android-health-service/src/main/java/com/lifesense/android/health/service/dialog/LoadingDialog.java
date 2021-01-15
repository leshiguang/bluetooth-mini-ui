package com.lifesense.android.health.service.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lifesense.android.health.service.R;


//公共loading弹出
public class LoadingDialog extends Dialog {

    private static final String TAG = "LoadingDialog";

    public LoadingDialog(Context context) {
        super(context, R.style.scale_Wait_Dialog);
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scale_dialog_loading);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.dimAmount = 0f;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //5.0 全透明实现
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

    }

}
