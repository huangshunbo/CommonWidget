package com.android.basiclib.net;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.basiclib.MApplication;
import com.android.basiclib.net.glide.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.integration.okhttp.OkHttpGlideModule;

/*
* 1.图片加载及处理
* */
public class ImageNetUtil {

    public static void load(String url , ImageView imageView){
        GlideApp.with(MApplication.application).load(url).into(imageView);
    }
    public static void load(String url , int placeholder, ImageView imageView){
        GlideApp.with(MApplication.application).load(url).placeholder(placeholder).into(imageView);
    }
    public static void miniThumb(String url , ImageView imageView){
        GlideApp.with(MApplication.application).load(url).miniThumb().into(imageView);
    }
    public static void miniThumb(String url ,int placeholder, ImageView imageView){
        GlideApp.with(MApplication.application).load(url).placeholder(placeholder).miniThumb().into(imageView);
    }

    public static void loadGif(String url , ImageView imageView){
        GlideApp.with(MApplication.application).asGif().load(url).into(imageView);
    }
    public static void loadGif(String url ,int placeholder, ImageView imageView){
        GlideApp.with(MApplication.application).asGif().load(url).placeholder(placeholder).into(imageView);
    }
}
