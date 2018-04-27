package com.android.commonwidget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListFragment extends Fragment {
    ListView mListView;
    private String[] mListStr = {"1","2","3","4"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListView = new ListView(getContext());
        return mListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, mListStr));
    }
}
