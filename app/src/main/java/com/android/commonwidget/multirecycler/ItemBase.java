package com.android.commonwidget.multirecycler;


import com.android.commonwidget.R;

public abstract class ItemBase {

    public abstract void onBindView(ViewHolderBase viewHolderBase);

    public abstract int getViewType();
}
