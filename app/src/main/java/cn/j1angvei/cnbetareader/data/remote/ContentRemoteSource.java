package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.converter.Converter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class ContentRemoteSource extends RemoteSource<Content> {

    public ContentRemoteSource(CnbetaApi api, Converter<Content> converter) {
        super(api, converter);
    }

    @Override
    public Observable<Content> getItem(int page, String... str) {
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
