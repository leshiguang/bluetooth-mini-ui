package com.lifesense.android.health.service.common.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lifesense.android.health.service.R;
import com.lifesense.utils.LanguageUtil;
import com.lifesense.utils.ui.StatusBarUtils;
import com.lifesense.android.health.service.devicedetails.widget.NavigationBar;
import com.lifesense.android.health.service.dialog.LoadingDialog;



/**
 * Create by qwerty
 * Create on 2020/6/8
 **/
public abstract class BaseActivity extends AppCompatActivity {

    protected NavigationBar mNavigationBar;
    private LoadingDialog loadingDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageUtil.setLocale(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.scale_activity_base);
        initNav(R.id.navigation_bar);
        FrameLayout flBaeContent = findViewById(R.id.fl_base_content);
        bindContentView(flBaeContent);
        StatusBarUtils.setColor(this,0x00000000);
        StatusBarUtils.setDarkMode(this);
        preInitView();
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    public void initNav(int navigateId) {
        mNavigationBar = findViewById(navigateId);
        if(mNavigationBar!=null){
            mNavigationBar.setLZNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        }
        mNavigationBar.setVisibility(showNav() ? View.VISIBLE : View.GONE);
        mNavigationBar.setLZTitle(getTitle());
    }

    protected void bindContentView(FrameLayout container) {
        LayoutInflater.from(this).inflate(getContentView(), container, true);
    }

    protected void preInitView(){

    }

    protected abstract @LayoutRes int getContentView();

    protected  void initViews(Bundle savedInstanceState){}

    protected  void initData(Bundle savedInstanceState){}

    protected boolean showNav() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onNavigationClick() {
        finish();
    }

    protected void showLoading() {
        if(loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading(){
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String[] string_permission, int request_code) {
        boolean flag = true;
        for (int i = 0; i < string_permission.length; i++) {
            if (ContextCompat.checkSelfPermission(this, string_permission[i]) != PackageManager.PERMISSION_GRANTED) {//已有权限
                flag = false;
                break;
            }
        }
        if(flag) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, string_permission, request_code);
            return false;
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if(mNavigationBar != null) {
            mNavigationBar.setLZTitle(title);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtil.setLocale(newBase));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageUtil.setLocale(this);
    }

    public <T extends ViewModel> T getViewModel(Class<T> tClass) {
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(tClass);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }
}
