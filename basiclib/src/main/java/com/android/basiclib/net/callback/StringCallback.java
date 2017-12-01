package com.android.basiclib.net.callback;

public abstract class StringCallback implements AbstractCallBack<String> {

    @Override
    public void onStart() {

    }

    @Override
    public String parseResponse(String result) {
        return result;
    }
}
