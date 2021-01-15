package com.lifesense.android.health.service.devicedetails.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import java.io.InputStream;

public class RescalableImageSpan extends ImageSpan {
    Uri mContentUri;
    private Drawable mDrawable;
    private Context mContext;
    public int mIntrinsicWidth = -1;
    public int mIntrinsicHeight = -1;
    private final int MAXWIDTH;

    public RescalableImageSpan(Context context, Uri uri, int maxwidth) {
        super(context, uri);
        mContext = context;
        mContentUri = uri;
        MAXWIDTH = maxwidth;
    }

    public RescalableImageSpan(Context context, int resourceId, int maxwidth) {
        super(context, resourceId);
        mContext = context;
        MAXWIDTH = maxwidth;
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable != null) {
            return mDrawable;
        } else if (mContentUri != null) {
            Bitmap bitmap = null;
            System.gc();
            try {
                InputStream is =
                        mContext.getContentResolver().openInputStream(mContentUri);
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, opt);
                is.close();
                is = mContext.getContentResolver().openInputStream(mContentUri);
                int width, height;
                width = opt.outWidth;
                height = opt.outHeight;
                mIntrinsicWidth = width;
                mIntrinsicHeight = height;
                if (opt.outWidth > MAXWIDTH) {
                    width = MAXWIDTH;
                    height = height * MAXWIDTH / opt.outWidth;
                    Rect padding = new Rect(0, 0, width, height);
                    bitmap = BitmapFactory.decodeStream(is, padding, null);
                } else {
                    bitmap = BitmapFactory.decodeStream(is);
                }
                mDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                mDrawable.setBounds(0, 0, width, height);
                is.close();
            } catch (Exception e) {
                return null;
            } catch (OutOfMemoryError e) {
                return null;
            }
        } else {
            mDrawable = super.getDrawable();
            rescaleBigImage(mDrawable);
            mIntrinsicWidth = mDrawable.getIntrinsicWidth();
            mIntrinsicHeight = mDrawable.getIntrinsicHeight();
        }
        return mDrawable;
    }

    private void rescaleBigImage(Drawable image) {
        if (MAXWIDTH < 0) {
            return;
        }
        int image_width = image.getIntrinsicWidth();
        int image_height = image.getIntrinsicHeight();

        if (image_width > MAXWIDTH) {
            image_width = MAXWIDTH;
            image_height = image_height * MAXWIDTH / image_width;
        }
        image.setBounds(0, 0, image_width, image_height);
    }
}