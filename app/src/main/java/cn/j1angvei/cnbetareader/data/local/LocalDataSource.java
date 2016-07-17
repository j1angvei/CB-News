package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.DataSource;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/16.
 */
@Singleton
public class LocalDataSource implements DataSource {

    @Inject
    public LocalDataSource() {
    }

    @Override
    public Observable<Article> getArticleFromSource(String type, int page) {
        return null;
    }

    @Override
    public Observable<Content> getContentFromSource(String sid) {
        return null;
    }

    @Override
    public Observable<Review> getReviewsFromSource(String type, int page) {
        return null;
    }

    @Override
    public Observable<Headline> getHeadlinesFromSource(String type, int page) {
        return null;
    }

    @Override
    public Observable<Article> getTopicArticlesFromSource(String topicId, int page) {
        return null;
    }

    @Override
    public Observable<Topic> getTopicsCoverByLetter(char letter) {
        return null;
    }
}
