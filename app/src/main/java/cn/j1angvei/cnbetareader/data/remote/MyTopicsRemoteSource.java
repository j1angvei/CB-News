package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsRemoteSource extends RemoteSource<Article> {

    @Inject
    public MyTopicsRemoteSource(CnbetaApi api, ArticleConverter converter) {
        super(api, converter);
    }

    @Override
    public Observable<Article> getData(String extra, Map<String, String> param) {
        return mCnbetaApi.getTopicNews(param)
                .flatMap(new Func1<ResponseBody, Observable<Article>>() {
                    @Override
                    public Observable<Article> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            return Observable.error(new ResponseParseException());
                        }
                    }
                });
    }
}
