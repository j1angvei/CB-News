package cn.j1angvei.cnbetareader.view;

import cn.j1angvei.cnbetareader.bean.Article;

/**
 * Created by Wayne on 2016/7/23.
 */
public interface NestedTopicsView extends BaseView {
    void renderArticle(Article article);

    void clearArticles();
}
