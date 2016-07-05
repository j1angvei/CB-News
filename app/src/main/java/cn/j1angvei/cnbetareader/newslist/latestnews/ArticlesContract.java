package cn.j1angvei.cnbetareader.newslist.latestnews;

import cn.j1angvei.cnbetareader.base.BasePresenter;
import cn.j1angvei.cnbetareader.base.BaseView;
import cn.j1angvei.cnbetareader.bean.Article;

/**
 * Created by Wayne on 2016/7/4.
 */
public interface ArticlesContract {
    interface Presenter extends BasePresenter<View> {
        void retrieveLatestNews();

        void retrieveMoreNews();
    }

    interface View extends BaseView {
        void addNews(Article article);

        void clearNews();

        String getSourceType();
    }
}
