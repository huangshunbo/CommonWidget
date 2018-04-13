package com.android.commonwidget.multirecycler;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class ViewHolderBase extends RecyclerView.ViewHolder{

    private MultiAdapter adapter;
    private View contentView;
    /*** 保存所有<id,view> 包括layout的 ***/
    private SparseArray<View> holderViews = new SparseArray<>();

    public ViewHolderBase(MultiAdapter adapter, View itemView) {
        super(itemView);
        this.adapter = adapter;
        contentView = itemView;
    }

    public void onBindViewHolder(ItemBase itemBase){
        itemBase.onBindView(this);
    }
    public int getViewType(){
        return getItemViewType();
    }

    public <T extends View> T get(Class<T> clazz,int id){
        View view = holderViews.get(id);
        if(view != null){
            return (T) view;
        }
        view = contentView.findViewById(id);
        if(view != null){
            holderViews.put(id,view);
        }
        return (T) view;
    }


}
