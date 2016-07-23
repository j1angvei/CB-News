package cn.j1angvei.cnbetareader.presenter;


import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.view.TopicView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
public class TopicPresenter<T> implements BasePresenter<TopicView> {
    TopicView mView;
    DataRepository<T> mRepository;

    public TopicPresenter(DataRepository<T> repository) {
        mRepository = repository;
    }

    public void retrieveTopics(int index) {
        //convert number 1-26 to letter a-z
        char letter = (char) ('a' + index - 1);
        mView.showLoading();
        mRepository.getTopics(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Topic topic) {

                    }

                });
    }


    @Override
    public void setView(TopicView view) {
        mView = view;

    }
}
