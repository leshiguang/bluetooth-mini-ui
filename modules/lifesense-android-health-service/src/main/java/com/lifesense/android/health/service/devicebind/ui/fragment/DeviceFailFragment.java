package com.lifesense.android.health.service.devicebind.ui.fragment;

import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;

/**
 * 设备绑定失败展示
 */
@FragmentWithArgs
public class DeviceFailFragment extends BaseFragment {
    @Arg(key = "IMG_URL")
    String imgUrl;
    private ConnectSearchViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_fail;
    }

    @Override
    protected void initData() {
        viewModel = getActivityViewModel(ConnectSearchViewModel.class);
        viewModel.setTitle(getString(R.string.scale_device_search_connect_title));
        getViewDataBinding().setVariable(BR.imageUrl, imgUrl);
        getViewDataBinding().setVariable(BR.listener, this);
    }

    public void onClick(View v) {
        viewModel.search();
    }
}
