package cn.j1angvei.cnbetareader.data.local;


import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.DataSource;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/16.
 */
public class LocalDataSource<T> implements DataSource<T> {

    public LocalDataSource() {
    }

    @Override
    public Observable<Content> getContent(String sid) {
        return null;
    }

    @Override
    public Observable<Comments> getComments(String token, String op) {
        return null;
    }


    @Override
    public Observable<T> getNews(String type, int page) {
        return null;
    }

    @Override
    public Observable<Article> getTopicArticles(String topicId, int page) {
        return null;
    }

    @Override
    public Observable<Topic> getTopics(char letter) {
        return null;
    }
}
