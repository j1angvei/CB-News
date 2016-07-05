package cn.j1angvei.cnbetareader.newslist.hotcomments;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/5.
 */
@PerFragment
public class ReviewPresenter implements ReviewContract.Presenter {
    private int mPage = 1;
    private ReviewContract.View mView;
    private DataRepository mRepository;

    @Inject
    public ReviewPresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveLatestReviews() {
        mPage = 1;
        mView.clearReviews();
        retrieveMoreReviews();
    }

    @Override
    public void retrieveMoreReviews() {
        mView.showLoading();
        mRepository.getReviewsFromSource(mView.getSourceType(), mPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Review>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Review review) {
                        mView.addReviews(review);
                    }
                });
    }

    @Override
    public void setView(ReviewContract.View view) {
        mView = view;
    }
}
