package cn.j1angvei.cnbetareader.presenter;


import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.ContentContract;
import cn.j1angvei.cnbetareader.data.repository.ContentRepository;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/21.
 */
//@PerFragment
@PerActivity
public class ContentPresenter implements ContentContract.Presenter {
    private final ContentRepository mRepository;
    private ContentContract.View mView;

    @Inject
    public ContentPresenter(ContentRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveContent(final ContentFragment fragment, String sid) {
        mView.showLoading();
        mRepository.getData(sid, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Content>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Content content) {
                        mView.setContent(fragment, content);
                        mView.saveContent(content);
                    }
                });
    }

    @Override
    public void setView(ContentContract.View view) {
        mView = view;
    }

}
