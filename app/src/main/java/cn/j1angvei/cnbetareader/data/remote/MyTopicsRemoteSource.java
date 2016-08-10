package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.converter.Converter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsRemoteSource extends RemoteSource<Article> {

    @Inject
    public MyTopicsRemoteSource(CnbetaApi api, @Named("c_article") Converter<Article> converter) {
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
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }
}
