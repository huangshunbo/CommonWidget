package com.android.commonwidget.repository;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.NetManager;
import com.android.basiclib.net.callback.AbstractCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainRepository {

    public static void requestTest(AbstractCallBack callback){
//        http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff?uri=/commonwidget/main/test
//        http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff?uri=/commonwidget/main/test
        NetManager.getInstance()
                .get("http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff")
                .addParam("uri","/commonwidget/main/test")
                .execute(callback);
    }
}
