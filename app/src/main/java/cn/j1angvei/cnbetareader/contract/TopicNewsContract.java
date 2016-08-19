package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Article;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface TopicNewsContract {
    interface View extends BaseView {
        void renderArticle(Article article);

        void clearArticles();
    }

    interface Presenter extends BasePresenter<TopicNewsContract.View> {
        void retrieveTopicNews(int page, String topicId);

    }
}
