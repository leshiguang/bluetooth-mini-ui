package com.lifesense.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lifesense.utils.StringUtil;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;

public class ImageUtil {

    private static ImageLoaderConfiguration sConfiguration = null;
    private static ImageSize sImageSize = null;
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        ImageLoader.getInstance().init(createDefaultImageLoaderConfig(context));

    }

    public static ImageLoaderConfiguration createDefaultImageLoaderConfig(Context context) {
        if (context == null) {
            return null;
        }
        if (sConfiguration == null) {
            synchronized (ImageUtil.class) {
                if (sConfiguration == null) {
                    sConfiguration = new ImageLoaderConfiguration.Builder(context)
                            .threadPoolSize(3) // default
                            .threadPriority(Thread.NORM_PRIORITY) // default
                            .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                            .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                            .memoryCacheSizePercentage(60) // default
                            .diskCacheSize(100 * 1024 * 1024).diskCacheFileCount(1000)
                            .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                            .defaultDisplayImageOptions(initDisplayOptions()) // default
                            .imageDecoder(new SafeImageDecoder(false))
                            .build();
                    sImageSize = new ImageSize(UIUtil.getScreenWidthPixels(context), UIUtil.getScreenHeightPixels(context));
                }
            }
        }
        return sConfiguration;
    }

    private static DisplayImageOptions initDisplayOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)  // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .considerExifParams(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new FadeInBitmapDisplayer(500, true, false, false)) // default
                .build();
        return options;

    }


    public static void displayImage(String url, ImageView imageView) {
        try {
            ImageLoader.getInstance().displayImage(url, imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static int getImgResidByName(String name) {
//        return R.mipmap.scale_icon_more;
//    }

    public static void load(String url, ImageView view) {
        load(url, view, -1);
    }

    public static void load(String url, ImageView view, int placeHolder) {
        load(url, view, null, placeHolder);
    }

    public static void load(String url, ImageView view, BitmapDisplayer bitmapDisplayer) {
        load(url, view, bitmapDisplayer, -1);
    }

    public static void load(String url, ImageView view, BitmapDisplayer bitmapDisplayer, int placeHolder) {
        load(url, view, bitmapDisplayer, placeHolder, -1);
    }

    /**
     * 加载图片
     *
     * @param url             图片url
     * @param view            ImageView
     * @param bitmapDisplayer {@see com.nostra13.universalimageloader.core.display.BitmapDisplayer}
     * @param placeHolder     加载占位图
     * @param errorHolder
     */
    public static void load(String url, ImageView view, BitmapDisplayer bitmapDisplayer, int placeHolder, int errorHolder) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        if (placeHolder > 0) {
            builder.showImageOnLoading(placeHolder);   // 设置加载占位图
            builder.showImageForEmptyUri(placeHolder);   // 设置空态占位图
        }
        if (errorHolder > 0) {
            builder.showImageOnFail(errorHolder); //设置失败图片
        }
        if (bitmapDisplayer != null) {
            builder.displayer(bitmapDisplayer);
        }
        builder.bitmapConfig(Bitmap.Config.ARGB_8888);
        builder.cacheInMemory(false);   // 设置下载的图片是否缓存在内存中
        builder.cacheOnDisk(true);

        ImageLoader.getInstance().displayImage(url, view, builder.build());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPhotoPathFromContentUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // DocumentProvider
            if (isExternalStorageDocument(uri)) {
                // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // MediaStore (and general)
            if (isGooglePhotosUri(uri)) {
                // Return the remote address
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }

        return null;
    }

    public static DisplayImageOptions
            circleoptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
            .displayer(new CircleBitmapDisplayer())  // 设置成圆形图片  new Displayer(0) 圆形
            .build();
    /**
     * 加载图片
     *
     * @param url
     * @param img
     * @param drawable
     */
    public static void disableImage(final String url, final ImageView img, final int drawable) {
        DisplayImageOptions
                options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(drawable)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawable)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .build();                                   // 创建配置过得DisplayImageOption对象
        if (StringUtil.isEmptyOrNull(url)) { // 为空的情况下加载
            ImageLoader.getInstance().displayImage("drawable://" + drawable, img, options);
        } else {
            ImageLoader.getInstance().displayImage(url, img, options);
        }
    }

    public static void displayImage(String imgUrl, ImageLoadingListener loadingListener) {
        if (imgUrl == null) {
            return;
        }
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
        ImageLoader.getInstance().loadImage(imgUrl, options, loadingListener);
    }

}
