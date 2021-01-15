package com.lifesense.android.health.service.common.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

/**
 * Create by qwerty
 * Create on 2021/1/7
 **/
public abstract class BaseViewModel extends ViewModel {

    public abstract void init(AppCompatActivity activity);

}
