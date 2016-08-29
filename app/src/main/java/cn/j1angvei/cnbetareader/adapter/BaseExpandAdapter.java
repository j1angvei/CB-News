package cn.j1angvei.cnbetareader.adapter;

import java.util.List;

/**
 * Created by Wayne on 2016/8/28.
 */

public interface BaseExpandAdapter<T> {
    void clearItems(int groupPosition);

    void addItem(int groupPosition, List<T> items);

    List<T> getSelectedItems();

    void clearSelectedItems();
}
