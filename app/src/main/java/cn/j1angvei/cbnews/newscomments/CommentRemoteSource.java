package cn.j1angvei.cbnews.newscomments;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.exception.ConvertFailException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class CommentRemoteSource extends RemoteSource<Comments> {

    @Inject
    public CommentRemoteSource(CBApiWrapper wrapper, @QCmt Converter<Comments> converter, NetworkUtil networkUtil) {
        super(wrapper, converter, networkUtil);
    }

    @Override
    public Observable<Comments> getComment(String sid, String sn) {
        return hasConnection() ? mApiWrapper.getComments(sid, sn)
                .flatMap(new Func1<ResponseBody, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            return Observable.error(new ConvertFailException());
                        }
                    }
                }) :
                super.getComment(sid, sn);
    }
}
