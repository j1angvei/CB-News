package cn.j1angvei.cnbetareader.presenter;

import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.view.SwipeView;

/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class SwipePresenter<T> {
    SwipeView<T> mView;
    DataRepository mRepository;

    public void setView(SwipeView<T> swipeView) {
        mView = swipeView;
    }

    public void retrieveItem(String type, int page) {
        mView.showLoading();
    }

}
