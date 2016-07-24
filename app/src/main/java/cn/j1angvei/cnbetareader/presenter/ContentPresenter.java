package cn.j1angvei.cnbetareader.presenter;


import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.view.ContentView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/21.
 */
public class ContentPresenter implements BasePresenter<ContentView> {
    private final NewsRepository<Content> mRepository;
    private ContentView mView;

    public ContentPresenter(NewsRepository<Content> repository) {
        mRepository = repository;
    }

    @Override
    public void setView(ContentView view) {
        mView = view;
    }

    public void retrieveContent(String sid) {
        mView.showLoading();
        mRepository.get(0, sid)
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
                        mView.renderItem(content);
                    }
                });
    }
}
