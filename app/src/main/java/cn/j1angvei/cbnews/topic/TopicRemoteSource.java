package cn.j1angvei.cbnews.topic;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.exception.ConvertFailException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
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

    @Inject
    public TopicRemoteSource(CBApiWrapper wrapper, @QTopic Converter<Topic> converter, NetworkUtil networkUtil) {
        super(wrapper, converter, networkUtil);
    }

    @Override
    public Observable<Topic> getTopics(final String letter) {
        return hasConnection() ?
                mApiWrapper.getTopics(letter)
                        .flatMap(new Func1<ResponseBody, Observable<Topic>>() {
                            @Override
                            public Observable<Topic> call(ResponseBody responseBody) {
                                try {
                                    return mConverter.toObservable(responseBody.string());
                                } catch (Exception e) {
                                    return Observable.error(new ConvertFailException());
                                }
                            }
                        })
                        .doOnNext(new Action1<Topic>() {
                            @Override
                            public void call(Topic topic) {
                                topic.setLetter(letter);
                            }
                        }) :
                super.getTopics(letter);
    }
}
