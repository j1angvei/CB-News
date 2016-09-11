package cn.j1angvei.cbnews.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.converter.ContentConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.exception.WEBItemNotFoundException;
import cn.j1angvei.cbnews.util.NetworkUtil;
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
    public ContentRemoteSource(CBApiWrapper wrapper, ContentConverter converter, NetworkUtil networkUtil) {
        super(wrapper, networkUtil);
        mConverter = converter;
    }

    @Override
    public Observable<Content> fetchData(int page, String id, String extra) {
        return hasConnection() ? mApiWrapper.getContent(id)
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
                }) :
                Observable.<Content>error(new WEBItemNotFoundException());
    }
}
