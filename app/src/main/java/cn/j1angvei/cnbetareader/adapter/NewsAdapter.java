package cn.j1angvei.cnbetareader.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class NewsAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements BaseAdapter<T> {
    abstract ArrayList<String> getSids();
}
