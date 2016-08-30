package cn.j1angvei.cnbetareader.presenter;


import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.ContentContract;
import cn.j1angvei.cnbetareader.data.repository.ContentRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/21.
 * get news content from ContentRepository
 */
@PerFragment
public class ContentPresenter implements ContentContract.Presenter {
    private final ContentRepository mRepository;
    private ContentContract.View mView;

    @Inject
    public ContentPresenter(ContentRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(ContentContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveContent(String sid) {
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
                        mView.onLoadFail(ErrorUtil.getErrorInfo(e));
                    }

                    @Override
                    public void onNext(Content content) {
                        mView.renderContent(content);
                    }
                });
    }
}
