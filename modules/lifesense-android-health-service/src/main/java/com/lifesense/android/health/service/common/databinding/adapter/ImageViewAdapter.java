package com.lifesense.android.health.service.common.databinding.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.lifesense.utils.ImageUtil;

/**
 * Create by qwerty
 * Create on 2020/12/29
 **/
public  class ImageViewAdapter {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }


    @BindingAdapter("imageUrl")
    public static void setSrc(ImageView imageView, String url) {
        ImageUtil.displayImage(url, imageView);
    }
    @BindingAdapter("selected")
    public static void setSelected(ImageView imageView, boolean select) {
        imageView.setSelected(select);
    }
}