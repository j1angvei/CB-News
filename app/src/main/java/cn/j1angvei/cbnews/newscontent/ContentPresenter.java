package cn.j1angvei.cbnews.newscontent;


import javax.inject.Inject;

import cn.j1angvei.cbnews.base.LoadMode;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.ErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/21.
 * get news content from ContentRepository
 */
@PerFragment
public class ContentPresenter implements ContentContract.Presenter {
    private final Repository<Content> mRepository;
    private ContentContract.View mView;

    @Inject
    public ContentPresenter(@QContent Repository<Content> repository) {
        mRepository = repository;
    }

    @Override
    public void setView(ContentContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveContent(int page, String sid) {
        mView.showLoading();
        mRepository.getContent(LoadMode.LOAD_REFRESH, sid)
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
