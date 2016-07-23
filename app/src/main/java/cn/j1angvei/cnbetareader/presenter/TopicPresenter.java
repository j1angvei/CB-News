package cn.j1angvei.cnbetareader.presenter;


import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.view.TopicView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
public class TopicPresenter implements BasePresenter<TopicView> {
    TopicView mView;
    NewsRepository<Topic> mRepository;

    public TopicPresenter(NewsRepository<Topic> repository) {
        mRepository = repository;
    }

    public void retrieveTopics(int index) {
        //convert number 1-26 to letter a-z
        String letter = String.valueOf((char) ('a' + index - 1));
        mView.showLoading();
        mRepository.get(0, letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Topic topic) {
                        mView.renderItem(topic);
                    }

                });
    }


    @Override
    public void setView(TopicView view) {
        mView = view;

    }
}
