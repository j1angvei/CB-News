package cn.j1angvei.cbnews.contract;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface NewsContract {
    interface View<T> extends BaseView {
        void renderNews(T news);

        void clearNewses();

        void onGetNewsFail(int message);
    }

    interface Presenter<T> extends BasePresenter<NewsContract.View<T>> {
        void retrieveNews(String type, int page);
    }
}
