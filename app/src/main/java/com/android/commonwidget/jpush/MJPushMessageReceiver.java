package com.android.commonwidget.jpush;

import android.content.Context;
import android.util.Log;

import com.android.basiclib.log.LogUtil;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MJPushMessageReceiver extends JPushMessageReceiver {

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        LogUtil.d(jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        LogUtil.d(jPushMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        LogUtil.d(jPushMessage);
    }
}
