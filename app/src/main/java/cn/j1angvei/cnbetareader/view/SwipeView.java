package cn.j1angvei.cnbetareader.view;

/**
 * Created by Wayne on 2016/7/9.
 */
public interface SwipeView<T> extends BaseView{

    void renderItem(T item);

    void clearItems();

}
