package cn.j1angvei.cbnews.data.remote;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.converter.TopicConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ServerItemNotFoundException;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 * get topics by letter from web
 */
@Singleton
public class TopicRemoteSource extends RemoteSource<Topic> {
    private static final String TAG = "TopicRemoteSource";
    private TopicConverter mConverter;

    @Inject
    public TopicRemoteSource(CBApiWrapper wrapper, TopicConverter converter, NetworkUtil networkUtil) {
        super(wrapper, networkUtil);
        mConverter = converter;
    }

    @Override
    public Observable<Topic> fetchData(@NonNull final Integer page, String... args) {
        return hasConnection() ?
                mApiWrapper.getTopics(page)
                        .flatMap(new Func1<ResponseBody, Observable<Topic>>() {
                            @Override
                            public Observable<Topic> call(ResponseBody responseBody) {
                                try {
                                    return mConverter.toObservable(responseBody.string());
                                } catch (Exception e) {
                                    return Observable.error(new ResponseParseException());
                                }
                            }
                        })
                        .doOnNext(new Action1<Topic>() {
                            @Override
                            public void call(Topic topic) {
                                topic.setPage(page);
                            }
                        })
                        .retry(1) :
                Observable.<Topic>error(new ServerItemNotFoundException());
    }
}
