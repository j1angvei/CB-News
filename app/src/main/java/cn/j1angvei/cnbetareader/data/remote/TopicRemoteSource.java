package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.Converter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class TopicRemoteSource extends RemoteSource<Topic> {

    public TopicRemoteSource(CnbetaApi api, Converter<Topic> converter) {
        super(api, converter);
    }

    @Override
    public Observable<Topic> getItem(int page, String... str) {
        String letter = str[0];
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
