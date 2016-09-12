package cn.j1angvei.cbnews.newslist;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cn.j1angvei.cbnews.base.BaseAdapter;


/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class NewsAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements BaseAdapter<T> {
    public abstract ArrayList<String> getSids();

}
