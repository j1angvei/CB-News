package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.converter.CommentsConverter;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;
import cn.j1angvei.cnbetareader.util.HeaderUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class CommentsRemoteSource extends RemoteSource<Comments> {
    @Inject
    public CommentsRemoteSource(CnbetaApi api, CommentsConverter converter) {
        super(api, converter);
    }

    @Override
    public Observable<Comments> getData(String sid, Map<String, String> param) {
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.getArticleComment(referer, param)
                .flatMap(new Func1<ResponseBody, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            return Observable.error(new ResponseParseException());
                        }
                    }
                });
    }


}
