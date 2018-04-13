package com.android.commonwidget.multirecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MultiAdapter extends RecyclerView.Adapter<ViewHolderBase> implements View.OnClickListener{

    RecyclerView recyclerView;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ItemBase> mItemBaseList = new ArrayList<>();
    private Class<? extends ItemBase>[] sorts = null;
    public MultiAdapter(RecyclerView recyclerView,ILayoutListener iLayoutListener){
        this.recyclerView = recyclerView;
        this.iLayoutListener = iLayoutListener;
        mContext = recyclerView.getContext();
        mInflater = LayoutInflater.from(mContext);
    }

    public List<ItemBase> getItems(){
        return mItemBaseList;
    }
    public void setItems(List<ItemBase> list){
        mItemBaseList = list;
        sort();
    }

    public void insertItems(List<ItemBase> items ){
        mItemBaseList.addAll(items);
        sort();
    }

    public <T extends ItemBase> void updateItems(List<T> items, Class clazz){
        List<ItemBase> tmpList = new ArrayList<>();
        tmpList.addAll(mItemBaseList);
        for(int i=0;i<tmpList.size();i++){
            ItemBase itemBase = tmpList.get(i);
            if(itemBase.getClass() == clazz){
                mItemBaseList.remove(itemBase);
            }
        }
        mItemBaseList.addAll(items);
        sort();
    }

    public void sortRule(Class<? extends ItemBase> ...clzs){
        sorts = clzs;
        sort();
    }
    public void sort(){
        if(sorts != null && mItemBaseList.size()>1){
            List<ItemBase> sortList = new ArrayList<>();
            List<ItemBase> unNeedSortList = new ArrayList<>();
            unNeedSortList.addAll(mItemBaseList);
            for(int i=0;i<sorts.length;i++){
                Class clz = sorts[i];
                for (ItemBase itemBase:mItemBaseList) {
                    if(itemBase.getClass() == clz){
                        unNeedSortList.remove(itemBase);
                        sortList.add(itemBase);
                    }
                }
            }
            mItemBaseList.clear();
            mItemBaseList.addAll(sortList);
            mItemBaseList.addAll(unNeedSortList);
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(iLayoutListener.oncreateLayout(viewType),null);
        itemView.setOnClickListener(this);
        return new ViewHolderBase(this,itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        holder.onBindViewHolder(mItemBaseList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mItemBaseList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mItemBaseList==null ? 0 : mItemBaseList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    ILayoutListener iLayoutListener;



    public interface ILayoutListener
    {
        int oncreateLayout(int viewType);
    }
    private OnItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}
