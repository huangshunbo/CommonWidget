package com.android.commonwidget.fragment.widget.bean;

import android.widget.TextView;

import com.android.commonwidget.R;
import com.android.commonwidget.multirecycler.ItemBase;
import com.android.commonwidget.multirecycler.ViewHolderBase;
import com.android.widgetlib.simplewidget.AutoWrapTextView;

public class TitleItem extends ItemBase {
    public static final int ITEM_TYPE = 1002;
    public static final int ITEM_LAYOUT = R.layout.item_title;
    String title;
    public TitleItem(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onBindView(ViewHolderBase viewHolderBase) {
        viewHolderBase.get(TextView.class,R.id.title).setText(title);
    }
    @Override
    public int getViewType() {
        return ITEM_TYPE;
    }
}
