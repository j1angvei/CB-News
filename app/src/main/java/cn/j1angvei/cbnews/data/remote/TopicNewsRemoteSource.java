package cn.j1angvei.cbnews.data.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.converter.ArticleConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class TopicNewsRemoteSource extends RemoteSource<Article> {
    private ArticleConverter mConverter;

    @Inject
    public TopicNewsRemoteSource(CBApiWrapper wrapper, ArticleConverter converter, NetworkUtil networkUtil) {
        super(wrapper, networkUtil);
        mConverter = converter;
    }

    @Override
    public Observable<Article> fetchData(@NonNull Integer page, @NonNull String... args) {
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
                })
                .retry(1);
    }
}
