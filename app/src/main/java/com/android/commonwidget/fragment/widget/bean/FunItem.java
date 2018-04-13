package com.android.commonwidget.fragment.widget.bean;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.commonwidget.R;
import com.android.commonwidget.multirecycler.ItemBase;
import com.android.commonwidget.multirecycler.ViewHolderBase;
import com.android.widgetlib.simplewidget.AutoWrapTextView;

public class FunItem extends ItemBase{

    public static final int ITEM_TYPE = 1003;
    public static final int ITEM_LAYOUT = R.layout.item_fun;

    String name;
    String time;
    String title;
    int imgRes;
    String ding,cai,zhuan,ping;

    public FunItem(String name, String time, String title, int imgRes, String ding, String cai, String zhuan, String ping) {
        this.name = name;
        this.time = time;
        this.title = title;
        this.imgRes = imgRes;
        this.ding = ding;
        this.cai = cai;
        this.zhuan = zhuan;
        this.ping = ping;
    }

    @Override
    public void onBindView(ViewHolderBase viewHolderBase) {
        viewHolderBase.get(TextView.class,R.id.item_fun_name).setText(name);
        viewHolderBase.get(TextView.class,R.id.item_fun_time).setText(time);
        viewHolderBase.get(AutoWrapTextView.class,R.id.item_fun_title).setText(title);
        viewHolderBase.get(ImageView.class,R.id.item_fun_img).setImageResource(imgRes);
        viewHolderBase.get(TextView.class,R.id.item_fun_ding).setText(ding);
        viewHolderBase.get(TextView.class,R.id.item_fun_cai).setText(cai);
        viewHolderBase.get(TextView.class,R.id.item_fun_zhuan).setText(zhuan);
        viewHolderBase.get(TextView.class,R.id.item_fun_ping).setText(ping);
    }

    @Override
    public int getViewType() {
        return ITEM_TYPE;
    }
}
