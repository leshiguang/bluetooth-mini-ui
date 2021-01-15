package com.lifesense.jumpaction.utils;

import android.content.Intent;
import android.os.Parcelable;

import com.lifesense.jumpaction.bean.LsBundle;
import com.lifesense.jumpaction.performer.activity.ActivityActionConstant;

import java.io.Serializable;

/**
 * Created by liuxinyi on 2017/4/17.
 */

public class IntentUtils {

    public static long getLongData(String key, Intent intent, long def) {
        long value = def;
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return value;
        }
        Parcelable data = getLsBundle(intent);//intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getLong(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = Long.parseLong(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    private static Parcelable getLsBundle(Intent intent) {
        return intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
    }

    public static int getIntData(String key, Intent intent, int def) {
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return def;
        }
        int value = def;
        Parcelable data = getLsBundle(intent);//intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getInt(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = Integer.parseInt(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static float getFloatData(String key, Intent intent, float def) {
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return def;
        }
        float value = def;

        Parcelable data = getLsBundle(intent);// intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getFloat(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = Float.parseFloat(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static double getDoubleData(String key, Intent intent, double def) {
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return def;
        }
        double value = def;

        Parcelable data = getLsBundle(intent);// intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getDouble(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = Double.parseDouble(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String getStringData(String key, Intent intent, String def) {
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return def;
        }
        String value = def;
        Parcelable data = getLsBundle(intent);//intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getString(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = o.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static Serializable getSerializable(String key, Intent intent) {
        if (intent == null) {
            return null;
        }
        if (intent.getExtras() == null) {
            return null;
        }
        return intent.getSerializableExtra(key);
    }

    public static Parcelable getParcelable(String key, Intent intent) {
        if (intent == null) {
            return null;
        }
        if (intent.getExtras() == null) {
            return null;
        }
        return intent.getParcelableExtra(key);
    }

    public static boolean getBoolean(String key, Intent intent, boolean def) {
        if (intent == null) {
            return def;
        }
        if (intent.getExtras() == null) {
            return def;
        }
        boolean value = def;

        Parcelable data = getLsBundle(intent);// intent.getExtras().getParcelable(ActivityActionConstant.ACTION_DATA);
        if (data != null && data instanceof LsBundle) {
            LsBundle lsBundle = (LsBundle) data;
            value = lsBundle.getBoolean(key, def);
        } else {
            Object o = intent.getExtras().get(key);
            if (o != null) {
                try {
                    value = Boolean.parseBoolean(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
