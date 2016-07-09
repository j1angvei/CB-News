package cn.j1angvei.cnbetareader.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class SwipeAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public abstract void clear();

    public abstract void add(T item);
}
