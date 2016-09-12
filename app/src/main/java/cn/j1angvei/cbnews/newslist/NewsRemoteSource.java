package cn.j1angvei.cbnews.newslist;

import android.text.TextUtils;

import java.io.IOException;

import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.exception.WEBItemNotFoundException;
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

    public NewsRemoteSource(CBApiWrapper wrapper, Converter<T> converter, NetworkUtil networkUtil) {
        super(wrapper, converter, networkUtil);
    }

    @Override
    public Observable<T> fetchData(int page, String id, final String extra) {
        boolean isTopicMews = TextUtils.isDigitsOnly(extra);
        return hasConnection() ?
                (isTopicMews ? mApiWrapper.getTopicNews(extra, page) : mApiWrapper.getNews(extra, page))
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
                                t.setType(extra);
                            }
                        }) :
                Observable.<T>error(new WEBItemNotFoundException());
    }
}
