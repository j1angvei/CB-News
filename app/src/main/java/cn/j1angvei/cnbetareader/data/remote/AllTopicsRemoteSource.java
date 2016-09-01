package cn.j1angvei.cnbetareader.data.remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.TopicConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CBApiWrapper;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 * get topics by letter from web
 */
@Singleton
public class AllTopicsRemoteSource extends RemoteSource<Topic> {
    private static final String TAG = "AllTopicsRemoteSource";
    private TopicConverter mConverter;

    @Inject
    public AllTopicsRemoteSource(CBApiWrapper wrapper, TopicConverter converter) {
       super(wrapper);
        mConverter = converter;
    }

    @Override
    public Observable<Topic> loadData(final int page, String... args) {
        return mApiWrapper.getTopics(page)
                .flatMap(new Func1<ResponseBody, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (Exception e) {
                            return Observable.error(new ResponseParseException());
                        }
                    }
                }).doOnNext(new Action1<Topic>() {
                    @Override
                    public void call(Topic topic) {
                        topic.setPage(page);
                    }
                });
    }
}
