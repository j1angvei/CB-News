package cn.j1angvei.cbnews.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.converter.CommentsConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.exception.WEBItemNotFoundException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class CommentRemoteSource extends RemoteSource<Comments> {
    private CommentsConverter mConverter;

    @Inject
    public CommentRemoteSource(CBApiWrapper wrapper, CommentsConverter converter, NetworkUtil networkUtil) {
        super(wrapper, networkUtil);
        mConverter = converter;
    }

    @Override
    public Observable<Comments> fetchData(int page, String id, String extra) {
        return hasConnection() ? mApiWrapper.getComments(id, extra)
                .flatMap(new Func1<ResponseBody, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            return Observable.error(new ResponseParseException());
                        }
                    }
                }) :
                Observable.<Comments>error(new WEBItemNotFoundException());
    }
}
