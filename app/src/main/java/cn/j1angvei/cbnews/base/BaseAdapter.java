package cn.j1angvei.cbnews.base;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface BaseAdapter<T> {
    void clear();

    void add(T item);
}
