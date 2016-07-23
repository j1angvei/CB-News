package cn.j1angvei.cnbetareader.data;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface DataSource<T> {

    Observable<T> getNews(String type, int page);

    Observable<Article> getTopicArticles(String topicId, int page);

    Observable<Topic> getTopics(char letter);

    Observable<Content> getContent(String sid);

    Observable<Comments> getComments(String token, String op);

}
