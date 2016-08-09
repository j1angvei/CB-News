package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Article;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface MyTopicsContract {
    interface View extends BaseView {
        void renderArticle(Article article);

        void clearArticles();
    }

    interface Presenter extends BasePresenter<MyTopicsContract.View> {
        void retrieveMyTopics(int page, String topicId);
    }
}
