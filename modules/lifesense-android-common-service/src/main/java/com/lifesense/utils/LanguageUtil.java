package com.lifesense.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public class LanguageUtil {
    public static final int ENGLISH = 0;
    public static final int CHINESE = 1;
    private static final String TAG = "LanguageUtil";
    private static Locale currentLocale;
    private static final String LANGUAGE = "language";
    private static final String LOCALE = "locale";

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentLocale = LocaleList.getDefault().get(0);
        } else {
            currentLocale = Locale.getDefault();
        }
    }

    public static void initLanguage(Context context) {
        setLocale(context);
        currentLocale = getLocaleByType(getLanguageType(context));
    }

    public static Context setLocale(Context context, int type) {
        if(context instanceof  Application) {
            setApplicationLocal(context,type);
        } else if(context.getApplicationContext() != null) {
            context = setNormalLocale(context, type);
            setApplicationLocal(context.getApplicationContext(),type);
        } else {
            context = setNormalLocale(context, type);
        }
        currentLocale = getLocaleByType(type);
        putLanguageType(context, type);
        Log.d(TAG, "setLocale: " + currentLocale.toString());
        return context;
    }


    private static Context setNormalLocale(Context context, int type) {
        //设置Activity
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(getLocaleByType(type));
            context = context.createConfigurationContext(config);
        } else {
            config.locale = getLocaleByType(type);
            resources.updateConfiguration(config, dm);
        }
        return context;
    }
    private static void setApplicationLocal(Context context, int type) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getLocaleByType(type);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    private static Locale getLocaleByType(int type) {
        Locale locale;
        switch (type) {
            case ENGLISH:
            default:
                locale = Locale.ENGLISH;
                break;
            case CHINESE:
                locale = Locale.CHINESE;
        }
        return locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static Context setLocale(Context context) {
        int type = getLanguageType(context);
        return setLocale(context,type);
    }

    public static boolean isSameLanguage(Context context) {
        int type = getLanguageType(context);
        return isSameLanguage(context, type);
    }

    public static boolean isChinese(Context context) {
        return getLanguageType(context) == 1;
    }

    public static boolean isSameLanguage(Context context, int type) {
        Locale locale = getLocaleByType(type);
        Locale appLocale = context.getResources().getConfiguration().locale;
        boolean equals = appLocale.equals(locale);
        Log.d(TAG, "isSameLanguage: " + locale.toString() + " / " + appLocale.toString() + " / " + equals);
        return equals;
    }

    private static void putLanguageType(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences(LANGUAGE, 0);
        Editor edit = sp.edit();
        edit.putInt(LOCALE, type);
        edit.apply();
    }

    private static int getLanguageType(Context context) {
        SharedPreferences sp = context.getSharedPreferences(LANGUAGE, 0);
        int type = sp.getInt(LOCALE, -1);
        if (type == -1) {
            if (currentLocale.getLanguage().equalsIgnoreCase(Locale.CHINESE.getLanguage())) {
                type = CHINESE;
            } else if (currentLocale.getLanguage().equalsIgnoreCase(Locale.ENGLISH.getLanguage())) {
                type = ENGLISH;
            } else {
                type = ENGLISH;
            }
        }

        return type;
    }
}