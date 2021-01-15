package com.lifesense.android.health.service.deviceota.model;

import android.content.Context;
import android.graphics.Color;
import android.text.style.AbsoluteSizeSpan;

import androidx.annotation.StringRes;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.util.ScreenUtils;
import com.lifesense.android.health.service.util.Spanny;
import com.lifesense.android.health.service.devicedetails.widget.RescalableImageSpan;

/**
 * Create by qwerty
 * Create on 2020/6/11
 * 升级状态
 **/
public enum UpgradeState {
    UPGRADE_INFO(new StateMessage[]{new StateMessage(R.string.scale_upgrade_info_new_version_msg,19),new StateMessage(R.string.scale_upgrade_info_current_version_msg,12)}, R.mipmap.scale_ic_upgrade_prepare, 100, Color.parseColor("#329AFF")),
    UPGRADING(new StateMessage[]{new StateMessage(R.string.scale_upgrading_msg,19)},0,0,Color.parseColor("#329AFF")),
    UPGRADE_INTERRUPT(new StateMessage[]{new StateMessage(R.string.scale_upgrade_interrupt_msg,19)},0,0, Color.parseColor("#DCDCDC")){
        @Override
        public int getProgress() {
            return UPGRADING.progress;
        }
    },
    UPGRADE_SUCCESS(new StateMessage[]{new StateMessage(R.string.scale_upgrade_success_msg,19),new StateMessage(R.string.scale_upgrade_success_version_msg,12)}, R.mipmap.scale_ic_upgrade_success,100, Color.parseColor("#7DD222"));

    private StateMessage[] stateMessages;
    private int iconRes;
    private int progress;
    private int circleColor;

    UpgradeState(StateMessage[] stateMessages, int iconRes, int defaultProgress, int circleColor) {
        this.stateMessages= stateMessages;
        this.iconRes = iconRes;
        this.progress = defaultProgress;
        this.circleColor = circleColor;
    }

    public CharSequence getMsg(Context context, Object... format) {
        Spanny spanny = new Spanny();
        String[] formatStr = format(context, format);
        for (int i = 0; i < stateMessages.length; i++) {
            StateMessage stateMessage = stateMessages[i];
            context.getString(stateMessage.resId);
            spanny.append(formatStr[i],new AbsoluteSizeSpan(stateMessage.size,true));
            if(i != stateMessages.length - 1) {
                spanny.append("\n");
            }
        }
        return spanny;
    }

    public CharSequence getIcon(Context context) {
        if (iconRes == 0) return "";
        Spanny icon = new Spanny();
        return icon.append("", new RescalableImageSpan(context, iconRes, ScreenUtils.dpInt2px(context, 194)));
    }

    private String[] format( Context context,Object... format) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stateMessages.length; i++) {
            StateMessage stateMessage = stateMessages[i];
            context.getString(stateMessage.resId);
            sb.append(context.getString(stateMessage.resId));
            if(i != stateMessages.length - 1) {
                sb.append("\n");
            }
        }
        return String.format(sb.toString(),format).split("\n");
    }

    public int getCircleColor() {
        return circleColor;
    }


    public UpgradeState setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public int getProgress() {
        return progress;
    }

    public static class StateMessage {
        @StringRes
        private int resId;

        private int size;

        public StateMessage(int resId, int size) {
            this.resId = resId;
            this.size = size;
        }
    }
}
