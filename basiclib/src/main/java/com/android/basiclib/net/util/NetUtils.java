package com.android.basiclib.net.util;

import com.alibaba.fastjson.JSON;
import com.android.basiclib.cryption.AbstractCryption;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NetUtils {

    public static String handleGetUrl(String url, HashMap<String, String> params) {
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }

    public static RequestBody handlePostParams(HashMap<String, String> params, AbstractCryption abstractCryption) {
//        FormBody.Builder builder = new FormBody.Builder();
//        if (params != null) {
//            Iterator<String> it = params.keySet().iterator();
//            while (it.hasNext()) {
//                String key = it.next();
//                String value = params.get(key);
//                builder.add(key,value);
//            }
//        }
        String mContent = abstractCryption.encrypt(JSON.toJSONString(params));
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mContent);
    }

    public static Headers handleHeaders(HashMap<String, String> params) {
        Headers.Builder builder = new Headers.Builder();
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                builder.add(key,value);
            }
        }
        return builder.build();
    }
}
