package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Optional;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.application.model.enums.ConfigStatus;
import com.lifesense.android.ble.core.serializer.AbstractConfig;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.common.ui.BaseViewModel;
import com.lifesense.android.health.service.devicedetails.ui.activity.BaseSettingActivity;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class ConfigViewModel<T extends AbstractConfig> extends BaseViewModel {

    private static final String TAG = BaseSettingActivity.class.getSimpleName();

    private MutableLiveData<List<T>> configs = new MutableLiveData<>();

    private MutableLiveData<T> updateConfig = new MutableLiveData<>();

    private MutableLiveData<T> deleteConfig = new MutableLiveData<>();

    private MutableLiveData<DeviceInfo> deviceInfo = new MutableLiveData<>();

    private MutableLiveData<ConfigStatus> configStatus = new MutableLiveData<>();

    @Override
    public void init(AppCompatActivity context) {
        deviceInfo.setValue(context.getIntent().getParcelableExtra("deviceInfo"));
        fetchConfigs();
    }

    public void fetchConfigs() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return;
        }
        Class<T> actualTypeArgument = (Class<T>) actualTypeArguments[0];
        List<T> data = (List<T>) BleDeviceManager.getDefaultManager().getConfigs(deviceInfo.getValue().getMac(), actualTypeArgument);
        if (!CollectionUtils.isEmpty(data)) {
            setUpdateConfig(data.get(0));
            setConfigs(data);
        } else {
            try {
                // 创建默认对象
                setUpdateConfig(actualTypeArgument.newInstance());
                setConfigs(Collections.EMPTY_LIST);
            } catch (Exception e) {
                Log.e(TAG, Optional.ofNullable(e.getMessage()).orElse("NPE"));
            }
        }
    }

    public MutableLiveData<List<T>> getConfigs() {
        return configs;
    }

    public MutableLiveData<T> getUpdateConfig() {
        return updateConfig;
    }

    public MutableLiveData<T> getDeleteConfig() {
        return deleteConfig;
    }

    public void setUpdateConfig(T updateConfig) {
        this.updateConfig.setValue(updateConfig);
    }

    public void setDeleteConfig(T deleteConfig) {
       this.deleteConfig.setValue(deleteConfig);
    }

    public MutableLiveData<ConfigStatus> getConfigStatus() {
        return configStatus;
    }

    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return null;
    }

    public MutableLiveData<String> getTitle() {
        MutableLiveData<String> title = new MutableLiveData<>();
        title.setValue(getTitleStr());
        return title;
    }

    public String getTitleStr() {
        return "";
    }

    public MutableLiveData<Integer> getTitleResId() {
        MutableLiveData<Integer> title = new MutableLiveData<>();
        title.setValue(getTitleId());
        return title;
    }

    public int getTitleId() {
        return 0;
    }


    private void setConfigs(List<T> configs) {
        this.configs.setValue(configs);
    }

    public void updateConfig() {
        T config = getUpdateConfig().getValue();
        getUpdateConfig().setValue(config);
        BleDeviceManager.getDefaultManager().updateConfig(deviceInfo.getValue().getMac(), config, new Consumer<ConfigStatus>() {
            @Override
            public void accept(ConfigStatus configStatus) throws Exception {
                ConfigViewModel.this.configStatus.postValue(configStatus);
            }
        });
    }

    public void deleteConfig(T config) {
        setDeleteConfig(config);
        BleDeviceManager.getDefaultManager().deleteConfig(deviceInfo.getValue().getMac(), config, new Consumer<ConfigStatus>() {
            @Override
            public void accept(ConfigStatus configStatus) throws Exception {
                ConfigViewModel.this.configStatus.postValue(configStatus);
            }
        });
    }

    public MutableLiveData<DeviceInfo> getDeviceInfo() {
        return deviceInfo;
    }
}
