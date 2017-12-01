package com.android.basiclib.net.callback;

public interface AbstractCallBack<T> {

    void onStart();

    void onSuccess(T t);

    void onFailure(Exception e);

    T parseResponse(String result);
}
