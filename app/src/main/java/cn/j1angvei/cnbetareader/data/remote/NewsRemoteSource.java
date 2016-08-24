package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsRemoteSource<T extends News> extends RemoteSource<T> {

    public NewsRemoteSource(CnbetaApi api, Converter<T> converter) {
        super(api, converter);
    }

    @Override
    public Observable<T> getData(final String sourceType, Map<String, String> param) {
        return mCnbetaApi.getNews(param)
                .flatMap(new Func1<ResponseBody, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            return Observable.error(new ResponseParseException());
                        }
                    }
                })
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        t.setSourceType(sourceType);
                    }
                });
    }

}
