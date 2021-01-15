/*
 * 文件名:  MediumTextView.java
 * 版权:   广州动心信息科技有限公司
 * 创建人:  liguoliang
 * 创建时间:2016-12-26
 */

package com.lifesense.android.health.service.devicebind.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.lifesense.android.health.service.R;

/**
 * Medium字体TextView
 * @author liguoliang
 * @date 16/12/26
 */

public class MediumTextView extends AppCompatTextView {
    public MediumTextView(Context context) {
        super(context);
        init();

    }

    public MediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.scale_text_apprarance_medium);
        } else {
            setTextAppearance(getContext(), R.style.scale_text_apprarance_medium);
        }
    }
}
