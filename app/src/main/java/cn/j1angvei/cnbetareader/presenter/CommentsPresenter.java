package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.view.CommentsView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/28.
 */
@PerFragment
public class CommentsPresenter implements BasePresenter<CommentsView> {
    private final CommentsRepository mRepository;
    private CommentsView mView;

    @Inject
    public CommentsPresenter(CommentsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(CommentsView view) {
        mView = view;
    }

    public void retrieveComments(String token, String op) {
        mView.showLoading();
        mRepository.get(0, token, op)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Comments comments) {
                        mView.renderItem(comments);
                    }
                });
    }

    public void operation() {

    }
}
