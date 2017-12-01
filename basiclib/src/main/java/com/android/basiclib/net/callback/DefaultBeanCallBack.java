package com.android.basiclib.net.callback;

import com.alibaba.fastjson.JSON;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.bean.DefaultBean;
import com.android.basiclib.net.exception.ApiException;

import java.lang.reflect.ParameterizedType;

public abstract class DefaultBeanCallBack<T> implements AbstractCallBack<T> {
    @Override
    public void onStart() {

    }

    @Override
    public T parseResponse(String result) {
        LogUtil.d(result);
        DefaultBean defaultBean = JSON.parseObject(result,DefaultBean.class);
        if(defaultBean.getCode() < 0){
            onFailure(new ApiException(defaultBean.getCode(),defaultBean.getMessage()));
            return null;
        }
        ParameterizedType pt = (ParameterizedType)getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>)pt.getActualTypeArguments()[0];
        T bean = JSON.parseObject(defaultBean.getData().toString(),clazz);
        defaultBean.setData(bean);
        return bean;
    }
}