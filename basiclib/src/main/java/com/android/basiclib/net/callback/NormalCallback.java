package com.android.basiclib.net.callback;

import com.alibaba.fastjson.JSON;
import com.android.basiclib.log.LogUtil;

import java.lang.reflect.ParameterizedType;

public abstract class NormalCallback<T> implements AbstractCallBack<T> {
    @Override
    public void onStart() {

    }

    @Override
    public T parseResponse(String result) {
        LogUtil.d(result);
        ParameterizedType pt = (ParameterizedType)getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>)pt.getActualTypeArguments()[0];
        T bean = JSON.parseObject(result,clazz);
        return bean;
    }
}
