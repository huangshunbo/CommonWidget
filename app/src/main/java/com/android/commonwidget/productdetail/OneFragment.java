package com.android.commonwidget.productdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.basiclib.fragment.AbstractMvpFragment;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.widgetlib.tbdetail.ConflictListView;
import com.android.widgetlib.tbdetail.ConflictScrollView;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends AbstractMvpFragment{
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConflictListView listView = new ConflictListView(getContext());
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1,getData()));
        return listView;
    }
    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        for(int i=0;i<20;i++){
            data.add("测试数据" + i);
        }
        return data;
    }
}
