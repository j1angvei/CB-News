package cn.j1angvei.cbnews.newscontent;


import java.util.Date;

import javax.inject.Inject;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Type;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.ErrorUtil;
import rx.Observable;
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
    private Repository<Bookmark> mBookmarkRepository;

    @Inject
    public ContentPresenter(@QContent Repository<Content> repository, @QBookmark Repository<Bookmark> bookmarkRepository) {
        mRepository = repository;
        mBookmarkRepository = bookmarkRepository;
    }

    @Override
    public void setView(ContentContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveContent(int page, String sid) {
        mView.showLoading();
        mRepository.getLatest(sid)
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

    @Override
    public void toBookmark(Content content) {
        Observable.just(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Content>() {
                    @Override
                    public void onCompleted() {
                        mView.showInfo(R.string.info_add_to_bookmark_success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showInfo(ErrorUtil.getErrorInfo(e));
                    }

                    @Override
                    public void onNext(Content content) {
                        Bookmark bookmark = new Bookmark();
                        bookmark.setSid(content.getSid());
                        bookmark.setTitle(content.getTitle());
                        bookmark.setTime(new Date());
                        bookmark.setType(Type.BOOKMARK);
                        mBookmarkRepository.storeToDb(bookmark);
                    }
                });

    }
}
