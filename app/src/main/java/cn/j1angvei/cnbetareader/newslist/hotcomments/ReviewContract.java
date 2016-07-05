package cn.j1angvei.cnbetareader.newslist.hotcomments;

import cn.j1angvei.cnbetareader.base.BasePresenter;
import cn.j1angvei.cnbetareader.base.BaseView;
import cn.j1angvei.cnbetareader.bean.Review;

/**
 * Created by Wayne on 2016/7/5.
 */
public interface ReviewContract {
    interface Presenter extends BasePresenter<View> {
        void retrieveLatestReviews();

        void retrieveMoreReviews();
    }

    interface View extends BaseView {
        void addReviews(Review review);

        void clearReviews();

        String getSourceType();
    }
}
