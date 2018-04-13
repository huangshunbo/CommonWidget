package com.android.commonwidget.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.basiclib.log.LogUtil;

public class MJPushBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d(intent);
    }
}
