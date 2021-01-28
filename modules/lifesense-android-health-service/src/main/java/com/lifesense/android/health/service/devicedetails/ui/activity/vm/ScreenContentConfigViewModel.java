package com.lifesense.android.health.service.devicedetails.ui.activity.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.lifesense.android.ble.core.application.model.config.Page;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.devicedetails.ui.activity.adapter.PedometerScreenContentRvAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Create by qwerty
 * Create on 2021/1/5
 **/
public class ScreenContentConfigViewModel extends ConfigViewModel<Page>{
    MutableLiveData<BaseDataBindingRvAdapter> adapter = new MutableLiveData<>();
    @Override
    public void init(AppCompatActivity context) {
        super.init(context);
        PedometerScreenContentRvAdapter adapter = new PedometerScreenContentRvAdapter();
        adapter.setSelectedPage(getUpdateConfig().getValue().getPageTypes());
        adapter.setItems(new ArrayList<>(Arrays.asList(Page.PageType.values())));
        adapter.setOnAdapterListener(new PedometerScreenContentRvAdapter.OnAdapterListener() {
            @Override
            public void onCheckedChange() {
                Page page = getUpdateConfig().getValue();
                page.setPageTypes(adapter.getSelectedPage());
                updateConfig();
            }

            @Override
            public void onDragStop() {
                Page page = getUpdateConfig().getValue();
                page.setPageTypes(adapter.getSelectedPage());
                updateConfig();
            }
        });
        this.adapter.setValue(adapter);
    }

    @Override
    public MutableLiveData<BaseDataBindingRvAdapter> getAdapter() {
        return adapter;
    }

    @Override
    public String getTitleStr() {
        return "屏幕内容设置";
    }
}
