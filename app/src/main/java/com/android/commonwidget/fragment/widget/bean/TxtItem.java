package com.android.commonwidget.fragment.widget.bean;

import android.widget.TextView;

import com.android.commonwidget.R;
import com.android.commonwidget.multirecycler.ItemBase;
import com.android.commonwidget.multirecycler.ViewHolderBase;
import com.android.widgetlib.simplewidget.AutoWrapTextView;

public class TxtItem extends ItemBase {

    public static final int ITEM_TYPE = 1001;
    public static final int ITEM_LAYOUT = R.layout.item_txt;

    String title;
    String content;
    public TxtItem(String title,String content){
        this.title = title;
        this.content = content;
    }
    @Override
    public void onBindView(ViewHolderBase viewHolderBase) {
        viewHolderBase.get(TextView.class,R.id.txt_title).setText(title);
        viewHolderBase.get(AutoWrapTextView.class,R.id.txt_content).setText(content);
    }
    @Override
    public int getViewType() {
        return ITEM_TYPE;
    }
}
