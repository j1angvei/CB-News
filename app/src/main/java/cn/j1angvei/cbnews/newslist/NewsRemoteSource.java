package cn.j1angvei.cbnews.newslist;

import android.text.TextUtils;

import java.io.IOException;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.exception.ConvertFailException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * get all type news from web
 */
public class NewsRemoteSource<T extends News> extends RemoteSource<T> {
    private static final String TAG = "NewsRemoteSource";

    public NewsRemoteSource(CBApiWrapper wrapper, Converter<T> converter, NetworkUtil networkUtil) {
        super(wrapper, converter, networkUtil);
    }

    @Override
    public Observable<T> getNews(int page, final String type) {
        boolean isTopicMews = TextUtils.isDigitsOnly(type);
        return hasConnection() ?
                (isTopicMews ? mApiWrapper.getTopicNews(type, page) : mApiWrapper.getNews(type, page))
                        .flatMap(new Func1<ResponseBody, Observable<T>>() {
                            @Override
                            public Observable<T> call(ResponseBody responseBody) {
                                try {
                                    return mConverter.toObservable(responseBody.string());
                                } catch (IOException e) {
                                    return Observable.error(new ConvertFailException());
                                }
                            }
                        })
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                t.setType(type);
                            }
                        }) :
                super.getNews(page, type);
    }
}
