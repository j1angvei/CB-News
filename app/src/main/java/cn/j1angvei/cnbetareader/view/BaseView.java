package cn.j1angvei.cnbetareader.view;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface BaseView<T> {
    void showLoading();

    void hideLoading();

    void renderItem(T item);

    void clearItems();
}
