package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.converter.ContentConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * get content from web
 */
@Singleton
public class ContentRemoteSource extends RemoteSource<Content> {
    @Inject
    public ContentRemoteSource(CnbetaApi api, ContentConverter converter) {
        super(api, converter);
    }

    @Override
    public Observable<Content> getData(String sid, Map<String, String> param) {
        return mCnbetaApi.getArticleContent(sid)
                .flatMap(new Func1<ResponseBody, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(ResponseBody responseBody) {
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
