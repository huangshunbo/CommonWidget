package com.android.basiclib.net.glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class CustomGlideExtension {
    private static final int MINI_THUMB_SIZE = 100;
    private static final RequestOptions DECODE_TYPE_GIF = GlideOptions.decodeTypeOf(GifDrawable.class).lock();

    private CustomGlideExtension(){}


    @GlideType(GifDrawable.class)
    public static void asGIF(RequestBuilder<GifDrawable> requestBuilder) {
        requestBuilder
                .transition(new DrawableTransitionOptions())
                .apply(DECODE_TYPE_GIF);
    }
    @GlideOption
    public static void miniThumb(RequestOptions options) {
        options
                .fitCenter()
                .override(MINI_THUMB_SIZE);
    }
}
