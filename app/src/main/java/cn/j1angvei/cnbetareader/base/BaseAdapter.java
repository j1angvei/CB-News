package cn.j1angvei.cnbetareader.base;

import java.util.ArrayList;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface BaseAdapter<T> {

    void clear();

    void add(T item);

    ArrayList<String> gatherNewsIds();

}
