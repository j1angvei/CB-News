package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.converter.ContentConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CBApiWrapper;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * get content from web
 */
@Singleton
public class ContentRemoteSource extends RemoteSource<Content> {
    private ContentConverter mConverter;

    @Inject
    public ContentRemoteSource(CBApiWrapper wrapper, ContentConverter converter) {
       super(wrapper);
        mConverter = converter;
    }

    @Override
    public Observable<Content> loadData(int page, String... args) {
        String sid = args[0];
        return mApiWrapper.getContent(sid)
                .flatMap(new Func1<ResponseBody, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Observable.error(new ResponseParseException());
                        }
                    }
                });
    }
}
