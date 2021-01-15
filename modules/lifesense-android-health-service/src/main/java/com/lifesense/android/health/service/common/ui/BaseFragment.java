package com.lifesense.android.health.service.common.ui;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

/**
 * Created by wrh on 16/1/4.
 */
public abstract class BaseFragment extends Fragment {

    public String TAG = this.getClass().getName();
    public Intent intent;
    private ViewDataBinding binder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater,getLayoutId(), container,false);
        return binder.getRoot();
    }

    abstract public @LayoutRes int getLayoutId();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initData();
    }

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        onShowChanged(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        onShowChanged(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onShowChanged(!hidden);
    }

    public void onShowChanged(boolean isShow) {
        // onResume只会在activity切换时调用
        // onHiddenChanged只会在页面切换时调用
        // 这两个接口都不完善,所以添加这个方法来处理页面切换监听
    }

    public <T extends ViewModel> T  getActivityViewModel(Class<T> tClass) {
        return new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(tClass);
    }

    public <T extends ViewModel> T getFragmentViewModel(Class<T> tClass) {
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(tClass);
    }

    @NonNull
    public String getStringById(@StringRes int resId) {
        if (getActivity() == null) {
            return "";
        }
        return getActivity().getString(resId);
    }

    public  <T extends ViewDataBinding> T getViewDataBinding() {
        return (T)binder;
    }
}
