package cn.j1angvei.cbnews.newscontent;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.exception.ConvertFailException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
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
    public ContentRemoteSource(CBApiWrapper wrapper, @QContent Converter<Content> converter, NetworkUtil networkUtil) {
        super(wrapper, converter, networkUtil);
    }

    @Override
    public Observable<Content> fetch(int page, String id, String extra) {
        return hasConnection() ? mApiWrapper.getContent(id)
                .flatMap(new Func1<ResponseBody, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Observable.error(new ConvertFailException());
                        }
                    }
                }) :
                super.fetch(page, id, extra);
    }
}
