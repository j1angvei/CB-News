package cn.j1angvei.cnbetareader.adapter;

/**
 * Created by Wayne on 2016/7/9.
 */
public interface SwipeAdapter<T> extends BaseAdapter {

    void clear();

    void add(T item);

}
