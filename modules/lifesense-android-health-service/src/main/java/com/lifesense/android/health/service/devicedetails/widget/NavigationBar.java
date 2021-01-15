package com.lifesense.android.health.service.devicedetails.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toolbar;
import androidx.annotation.NonNull;

import com.lifesense.android.health.service.R;


/**
 *
 */
public class NavigationBar extends Toolbar {

    private ImageButton btNavigation;

    private TextView tvTitle;

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.scale_layout_navigationbar,this,true);
        btNavigation = findViewById(R.id.bt_navigation);
        tvTitle = findViewById(R.id.tv_title);
        //自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.scale_NavigationBar, defStyleAttr, 0);
        Drawable navigationIcon = typedArray.getDrawable(R.styleable.scale_NavigationBar_scale_lz_navigationIcon);
        if(navigationIcon != null) {
            setLZNavigationIcon(navigationIcon);
        }
        String title = typedArray.getString(R.styleable.scale_NavigationBar_scale_lz_title);
        setLZTitle(title);
        int titleColor = typedArray.getColor(R.styleable.scale_NavigationBar_scale_lz_titleColor, Color.BLACK);
        setLZTitleColor(titleColor);
    }

    public void setLZNavigationIcon(@NonNull Drawable drawable) {
        btNavigation.setImageDrawable(drawable);
    }

    public void setLZTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setLZTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setLZNavigationClickListener(OnClickListener onClickListener) {
        btNavigation.setOnClickListener(onClickListener);
    }

}
