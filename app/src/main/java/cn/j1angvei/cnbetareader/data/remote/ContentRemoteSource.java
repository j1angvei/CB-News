package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.converter.Converter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class ContentRemoteSource extends RemoteSource<Content> {
    @Inject
    public ContentRemoteSource(CnbetaApi api, @Named("c_content") Converter<Content> converter) {
        super(api, converter);
    }

    @Override
    public Observable<Content> getItem(int page, String... str) {
        assert str[0] != null;
        String sid = str[0];
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
