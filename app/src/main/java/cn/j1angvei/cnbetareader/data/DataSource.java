package cn.j1angvei.cnbetareader.data;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface DataSource {

    Observable<Article> getArticleFromSource(String type, int page);

    Observable<Content> getContentFromSource(String sid);

    Observable<Review> getReviewsFromSource(String type, int page);

    Observable<Headline> getHeadlinesFromSource(String type, int page);

    Observable<Article> getTopicArticlesFromSource(String topicId, int page);

    Observable<Topic> getTopicsCoverByLetter(char letter);
}
