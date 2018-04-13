package com.android.commonwidget.activity;

import com.android.basiclib.data.DLocal;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import com.android.commonwidget.constact.Constact;
import com.android.widgetlib.gesturecipher.GestureView;

public abstract class BaseActivity extends AbstractMvpActivity implements GestureView.IGesturePasswordListener {
    private static final int GESTURE_CIPHER_TIME = 1000 * 60 * 5;//以毫秒为单位，5分钟
    GestureView gestureView;
    @Override
    protected void onCheckEvenyTime() {
        super.onCheckEvenyTime();
        long current = System.currentTimeMillis() / 1000 ;
        long lastTime = DLocal.getLong(Constact.DLOCAL_GESTURE_CIPHER_CHECK_VAL,0L);
        if(current - lastTime > GESTURE_CIPHER_TIME){
            gestureView = new GestureView(this,null);
            gestureView.setParentView(getRootView());
            gestureView.setmIgesturePasswordListener(this);
        }
        DLocal.save(Constact.DLOCAL_GESTURE_CIPHER_CHECK_VAL,current);
    }


    @Override
    public void onGesCipherStart() {

    }

    @Override
    public void onError(int code, String msg, String pwd) {

    }

    @Override
    public void onFirstPwd(String pwd) {

    }

    @Override
    public void onSecondPwd(String pwd) {
        getRootView().removeView(gestureView);
    }
}
