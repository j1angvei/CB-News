package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.util.JsonpUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsRemoteSource<T> extends RemoteSource<T> {

    public NewsRemoteSource(CnbetaApi api, Converter<T> converter) {
        super(api, converter);
    }

    @Override
    public Observable<T> getItem(int page, String... str) {
        String callback = JsonpUtil.getParameter();
        long timeStamp = JsonpUtil.getInitTime() + page;
        String type = str[0];
        return mCnbetaApi.getNews(callback, type, page, timeStamp)
                .flatMap(new Func1<ResponseBody, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseBody responseBody) {
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
