package com.android.commonwidget.productdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.basiclib.fragment.AbstractMvpFragment;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;
import com.android.widgetlib.tbdetail.ConflictScrollView;

public class TwoFragment extends AbstractMvpFragment{
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConflictScrollView scrollView = new ConflictScrollView(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i=0;i<20;i++){
            TextView textView = new TextView(getContext());
            textView.setText("Hello,"+i);
            textView.setPadding(20,50,0,50);
            linearLayout.addView(textView);
        }
        scrollView.addView(linearLayout);
        return scrollView;
    }
}
