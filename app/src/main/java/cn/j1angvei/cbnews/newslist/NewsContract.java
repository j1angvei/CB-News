package cn.j1angvei.cbnews.newslist;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface NewsContract {
    interface View<T> extends BaseView {
        void renderNews(T news);

        void clearNewses();

        void onGetNewsFail(int message);
    }

    interface Presenter<T> extends BasePresenter<View<T>> {
        void retrieveNews(String type, int page);
    }
}
