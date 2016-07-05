package cn.j1angvei.cnbetareader.newslist.pastheadlines;

import cn.j1angvei.cnbetareader.base.BasePresenter;
import cn.j1angvei.cnbetareader.base.BaseView;
import cn.j1angvei.cnbetareader.bean.Headline;

/**
 * Created by Wayne on 2016/7/5.
 */
public class HeadlinesContract {
    interface Presenter extends BasePresenter<View> {
        void retrieveLatestHeadlines();

        void retrieveMoreHeadlines();
    }

    interface View extends BaseView {
        void addHeadlines(Headline headline);

        void clearHeadlines();

        String getSourceType();
    }
}
