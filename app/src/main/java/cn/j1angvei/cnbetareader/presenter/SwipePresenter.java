package cn.j1angvei.cnbetareader.presenter;

import cn.j1angvei.cnbetareader.view.SwipeView;

/**
 * Created by Wayne on 2016/7/9.
 */
public interface SwipePresenter<T> {
    void setView(SwipeView<T> swipeView);

    void retrieveItem(String type, int page);
}
