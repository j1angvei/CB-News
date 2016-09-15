package cn.j1angvei.cbnews.base;

/**
 * Created by Wayne on 2016/9/13.
 */

public interface LoadPresenter<V> extends BasePresenter<V> {

    void refreshData();

    void moreData();
}
