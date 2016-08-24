package cn.j1angvei.cnbetareader.data.remote;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.TopicConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
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
public class ExploreRemoteSource extends RemoteSource<Topic> {
    private static final String TAG = "ExploreRemoteSource";

    @Inject
    public ExploreRemoteSource(CnbetaApi api, TopicConverter converter) {
        super(api, converter);
    }

    @Override
    public Observable<Topic> getData(final String letter, Map<String, String> param) {
        return mCnbetaApi.getTopics(letter)
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
                        topic.setLetter(letter);
                    }
                });
    }

}
