package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class ExplorePresenter extends SwipePresenter<Topic> {

    @Inject
    public ExplorePresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveItem(int index) {
        super.retrieveItem(index);
        //convert number 1-26 to letter a-z
        char letter = (char) ('a' + index - 1);
        mView.showLoading();
        mRepository.getTopicsCoverByLetter(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Topic topic) {
                        mView.renderItem(topic);
                    }
                });
    }

}
