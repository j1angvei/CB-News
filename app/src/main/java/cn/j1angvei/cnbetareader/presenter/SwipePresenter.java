package cn.j1angvei.cnbetareader.presenter;

import cn.j1angvei.cnbetareader.view.SwipeView;

/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class SwipePresenter<T> {
    public abstract void setView(SwipeView<T> swipeView);

    public abstract void retrieveItem(String type, int page);
}
