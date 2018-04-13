package com.android.commonwidget.fragment.me;

import com.android.basiclib.fragment.AbstractMvpFragment;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;

@ELayout(Layout = R.layout.fragment_me)
public class MeFragment extends AbstractMvpFragment {
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isisIntrusionStatuBar() {
        return true;
    }
}
