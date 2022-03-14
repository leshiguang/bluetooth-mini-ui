package com.lifesense.android.health.service.devicedetails.repository;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Optional;
import com.lifesense.android.ble.core.serializer.AbstractConfig;
import com.lifesense.android.ble.core.serializer.DataType;
import com.lifesense.android.health.service.prefs.PreferenceStorage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2021/1/27
 **/
public class ConfigsRepository {

    private static final String TAG = ConfigsRepository.class.getSimpleName();
    /**
     * 保存配置项
     * @param mac
     * @param config
     */
    public static void saveConfig(String mac, AbstractConfig config) {
        String key = mac + "_" + config.getClass().getName();
        List list;
        String cache =  PreferenceStorage.getString(key);

        if (TextUtils.isEmpty(cache)) {
            //新增的设置项
            list = Arrays.asList(config);
        } else {
            // 更新缓存
            list = JSONObject.parseArray(cache,config.getClass());
            int i = list.indexOf(config);
            if (i < 0) {
                list.add(config);
            } else {
                list.remove(config);
                list.add(i, config);
            }
        }
        //存储list结果
        PreferenceStorage.putString(key, JSONArray.toJSONString(list));

    }

    /**
     * 获取保存的设置项
     * @param mac
     * @param clazz
     * @return
     */
    public static List<? extends AbstractConfig> getConfigs(String mac, Class<? extends AbstractConfig> clazz) {
        String key = mac + "_" + clazz.getName();

        String cache =  PreferenceStorage.getString(key);
        try {
            if (TextUtils.isEmpty(cache)) {
                //新增的设置项, 部分有缺省值
                List<? extends AbstractConfig> defaultValue = clazz.newInstance().defaultValue();
                PreferenceStorage.putString(key,JSON.toJSONString(defaultValue));
                return defaultValue;
            }
        } catch (Exception e) {
            Log.i(TAG, Optional.ofNullable(e.getMessage()).orElse("NPE"));
            return Collections.emptyList();
        }
        return JSON.parseArray(cache, clazz);

    }

    /**
     * 删除保存的设置项
     * @param mac
     * @param config
     */
    public static void deleteConfig(String mac, AbstractConfig config) {

        String key = mac + "_" + config.getClass().getName();

        String cache =  PreferenceStorage.getString(key);
        if (TextUtils.isEmpty(cache)) {
            //新增的设置项
            return;
        }
        List<? extends AbstractConfig> abstractConfigs = JSON.parseArray(cache, config.getClass());
        abstractConfigs.remove(config);
        PreferenceStorage.putString(key, JSON.toJSONString(abstractConfigs));

    }
}
