package cn.j1angvei.cnbetareader.view;

/**
 * Created by Wayne on 2016/7/9.
 */
public interface NewsView<T> extends BaseView {
    void renderNews(T news);

    void clearNewses();
}
