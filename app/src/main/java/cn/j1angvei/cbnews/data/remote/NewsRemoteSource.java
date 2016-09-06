package cn.j1angvei.cbnews.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.converter.NewsConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * get all type news from web
 */
public class NewsRemoteSource<T extends News> extends RemoteSource<T> {
    private NewsConverter<T> mConverter;

    public NewsRemoteSource(CBApiWrapper wrapper, NewsConverter<T> converter, NetworkUtil networkUtil) {
        super(wrapper, networkUtil);
        mConverter = converter;
    }

    @Override
    public Observable<T> fetchData(@NonNull Integer page, @NonNull String... args) {
        final String sourceType = args[0];
        boolean isTopicMews = TextUtils.isDigitsOnly(sourceType);
        return (isTopicMews ? mApiWrapper.getTopicNews(sourceType, page) : mApiWrapper.getNews(sourceType, page))
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
                })
                .retry(1);
    }
}
