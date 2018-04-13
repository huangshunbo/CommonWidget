package com.android.basiclib.net.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class CustomAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSizeBytes = 1024 * 1024 * 20; // 图片缓存 20mb
        //内存缓存
        builder.setMemoryCache(new LruResourceCache(cacheSizeBytes));
        //磁盘缓存
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeBytes));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

