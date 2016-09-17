package cn.j1angvei.cbnews.newslist;

import java.util.List;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.LoadView;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface NewsContract {
    interface View<T> extends LoadView {
        void renderNews(List<T> news);

        void clearNews();

        void showInfo(int infoId);
    }

    interface Presenter<T> extends BasePresenter<View<T>> {
        void cache(String type);

        void refresh(String type);

        void more(String type);

    }
}
