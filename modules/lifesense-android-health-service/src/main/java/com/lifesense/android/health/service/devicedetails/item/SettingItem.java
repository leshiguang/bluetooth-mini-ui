package com.lifesense.android.health.service.devicedetails.item;


import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;

import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.ble.core.serializer.AbstractConfig;
import com.lifesense.android.ble.core.valueobject.DeviceInfo;
import com.lifesense.android.health.service.devicedetails.item.builder.ItemType;
import com.lifesense.android.health.service.devicedetails.repository.ConfigsRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2020/9/22
 **/

public abstract class SettingItem<T extends AbstractConfig> extends BaseObservable implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /**
     * 标题
     */
    protected String title;

    /**
     * 值
     */
    protected String value;

    /**
     * 类型
     */
    protected ItemType itemType = ItemType.LinkText;

    /**
     * 如果是switch开关， 该字段可用
     */
    protected boolean openSwitch;

    /**
     * 数据
     */
    protected List<T> configs;

    /**
     * tag内容
     */
    protected String tagText;

    protected AppCompatActivity context;

    /**
     * 设备信息
     */
    protected DeviceInfo deviceInfo;

    public SettingItem() {
    }

    public void SettingItem(AppCompatActivity context, DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        this.context = context;
        fetchItemConfigs();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {
        if (getTargetAction() != null) {
            Intent intent = new Intent(context, getTargetAction());
            intent.putExtra("deviceInfo", deviceInfo);
            //暂时冗余的
            intent.putExtra("mac_extra", deviceInfo.getMac());
            context.startActivity(intent);
        }
    }

    /**
     * 获取点击行为目标页面
     * @return
     */
    public abstract Class getTargetAction();

    /**
     * 拉取配置数据
     */
    public void fetchItemConfigs() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return;
        }
        Type actualTypeArgument = actualTypeArguments[0];
        configs = (List<T>) ConfigsRepository.getConfigs(deviceInfo.getMac(), (Class<? extends AbstractConfig>) actualTypeArgument);
    }


    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }


    public boolean isOpenSwitch() {
        return openSwitch;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }


    public ItemType getItemType() {
        return itemType;
    }

    public AppCompatActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setOpenSwitch(boolean openSwitch) {
        this.openSwitch = openSwitch;
    }

    public void setConfigs(List<T> configs) {
        this.configs = configs;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
}
