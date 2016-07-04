package cn.j1angvei.cnbetareader.data;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface DataSource {

    Observable<Article> getArticleFromSource(String type, int page);

    Observable<Content> getContentFromSource(String sid);
}
