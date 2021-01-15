package com.lifesense.android.health.service.monitor;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by qwerty
 * Create on 2020/11/10
 **/
public class Monitor {
    public Monitor(Application application) {
        init(application);
    }

    public void init(Application application) {
        Class aClass = null;
        try {
//            aClass = Class.forName("com.didichuxing.doraemonkit.DoraemonKit");
//            Method method  = aClass.getMethod("install", Application.class, String.class);
//            method.invoke(null,application,"");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
