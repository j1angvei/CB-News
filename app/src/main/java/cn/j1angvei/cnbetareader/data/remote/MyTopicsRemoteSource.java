package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CBApiWrapper;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsRemoteSource extends RemoteSource<Article> {
    private ArticleConverter mConverter;

    @Inject
    public MyTopicsRemoteSource(CBApiWrapper wrapper, ArticleConverter converter) {
       super(wrapper);
        mConverter = converter;
    }

    @Override
    public Observable<Article> loadData(int page, String... args) {
        String topicId = args[0];
        return mApiWrapper.getTopicNews(topicId, page)
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
