package com.android.commonwidget.repository;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.NetManager;
import com.android.basiclib.net.callback.AbstractCallBack;
import com.android.commonwidget.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainRepository {

    public static void requestTest(AbstractCallBack callback){
//        http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff?uri=/commonwidget/main/test
//        http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff?uri=/commonwidget/main/test
        NetManager.create()
//                .get("http://result.eolinker.com/AjKXYkLadba7ef081cf3188e40219f7ea64b3a11a80b5ff")
                .get("http://www.baidu.com")
                .addParam("uri","/commonwidget/main/test")
                .addLocalMockData(R.raw.mock_data)
                .execute(callback);
    }
}
