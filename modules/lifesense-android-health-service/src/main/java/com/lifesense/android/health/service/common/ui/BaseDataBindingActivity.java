package com.lifesense.android.health.service.common.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create by qwerty
 * Create on 2020/6/8
 **/
public abstract class BaseDataBindingActivity<VM extends BaseViewModel> extends BaseActivity {
    protected ViewDataBinding viewDataBinding;
    protected VM viewModel;

    @Override
    protected void bindContentView(FrameLayout container) {
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getContentView(), container, true);
        viewDataBinding.setLifecycleOwner(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return;
        }

        Class<VM> actualTypeArgument = (Class<VM>) actualTypeArguments[0];
        viewModel = getViewModel(actualTypeArgument);
        if (viewModel == null) {
            return;
        }
        viewModel.init(this);
        //绑定数据
        viewDataBinding.setVariable(getViewModelVariableId(), viewModel);
    }

    /**
     * 获取参数ID
     *
     * @return
     */
    public abstract int getViewModelVariableId();

}
