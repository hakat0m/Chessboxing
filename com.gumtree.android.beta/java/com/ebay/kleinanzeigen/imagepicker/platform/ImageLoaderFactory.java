package com.ebay.kleinanzeigen.imagepicker.platform;

import android.content.Context;
import android.graphics.Bitmap.Config;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions$Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderFactory {
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader(Context context) {
        if (imageLoader == null || !imageLoader.isInited()) {
            initImageLoader(context);
        }
        return imageLoader;
    }

    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration$Builder(context.getApplicationContext()).defaultDisplayImageOptions(new DisplayImageOptions$Builder().imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Config.RGB_565).resetViewBeforeLoading(true).build()).memoryCache(new WeakMemoryCache()).diskCacheExtraOptions(480, 320, null).threadPoolSize(5).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(build);
    }

    public static void clearCaches() {
        if (imageLoader != null && imageLoader.isInited()) {
            imageLoader.clearDiskCache();
            imageLoader.clearMemoryCache();
        }
    }
}
