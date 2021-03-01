package com.lifesense.android.health.service.devicebind.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.InputMethodManager;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.lifesense.android.ble.core.application.BleDeviceManager;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.fragment.argsbuilder.SerializableArgsBuilder;
import com.lifesense.android.health.service.common.ui.BaseFragment;
import com.lifesense.android.health.service.common.LSEDeviceInfoApp;
import com.lifesense.android.health.service.databinding.ScaleFragmentDeviceInputCodeBinding;
import com.lifesense.android.health.service.devicebind.ui.vm.ConnectSearchViewModel;
import com.lifesense.android.health.service.devicebind.widget.SecurityCodeView;

/**
 * 输入设备随机码
 */
@FragmentWithArgs
public class DeviceInputCodeFragment extends BaseFragment {
    private final int TIME_OUT_TIME = 55000;
    @Arg(key = "DEVICE_INFO_APP", bundler = SerializableArgsBuilder.class)
    LSEDeviceInfoApp lseDeviceInfoApp;
    private Handler timeoutHandler = new Handler(Looper.getMainLooper());
    private ConnectSearchViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.scale_fragment_device_input_code;
    }

    @Override
    protected void initData() {
        viewModel = getActivityViewModel(ConnectSearchViewModel.class);

        timeoutHandler.postDelayed(timeoutRunnable, TIME_OUT_TIME);
        ScaleFragmentDeviceInputCodeBinding binding = getViewDataBinding();
        binding.scvEdittext.setDefaultCount(6);
        binding.scvEdittext.setInputCompleteListener(new SecurityCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {

                InputMethodManager imm = (InputMethodManager) binding.scvEdittext.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(binding.scvEdittext.getApplicationWindowToken(), 0);
                }

                String code = binding.scvEdittext.getEditContent();
                viewModel.bind(lseDeviceInfoApp);
                BleDeviceManager.getDefaultManager().inputRandomNumber(lseDeviceInfoApp.getMacAddress(), code);
                //移除超时任务
                timeoutHandler.removeCallbacks(timeoutRunnable);
            }

            @Override
            public void deleteContent(boolean isDelete) {

            }
        });

        binding.scvEdittext.clearEditText();
        binding.scvEdittext.showSoftInput();
    }

    public void cancelBind() {
        BleDeviceManager.getDefaultManager().unBind(lseDeviceInfoApp.getLSEDeviceInfo().getMac());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timeoutHandler.removeCallbacks(timeoutRunnable);
    }

    Runnable timeoutRunnable = () -> BleDeviceManager.getDefaultManager().unBind(lseDeviceInfoApp.getLSEDeviceInfo().getMac());


}
