package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 * get topics by letter from web
 */
@Singleton
public class ExploreRemoteSource extends RemoteSource<Topic> {
    @Inject
    public ExploreRemoteSource(CnbetaApi api, @Named("c_topic") Converter<Topic> converter) {
        super(api, converter);
    }

    @Override
    public Observable<Topic> getData(String letter, Map<String, String> param) {
        return mCnbetaApi.getTopics(letter)
                .flatMap(new Func1<ResponseBody, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

}
