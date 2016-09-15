package cn.j1angvei.cbnews.base;

import cn.j1angvei.cbnews.exception.temp.GetCommentFailException;
import cn.j1angvei.cbnews.exception.temp.GetContentFailException;
import cn.j1angvei.cbnews.exception.temp.GetNewsFailException;
import cn.j1angvei.cbnews.exception.temp.GetTopicFailException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    protected final CBApiWrapper mApiWrapper;
    protected final Converter<T> mConverter;
    private final NetworkUtil mNetworkUtil;

    public RemoteSource(CBApiWrapper wrapper, Converter<T> converter, NetworkUtil networkUtil) {
        mApiWrapper = wrapper;
        mConverter = converter;
        mNetworkUtil = networkUtil;
    }

    public Observable<T> getNews(int page, String type) {
        return Observable.error(new GetNewsFailException());
    }

    public Observable<T> getContent(String sid) {
        return Observable.error(new GetContentFailException());
    }

    public Observable<T> getComment(String sid, String sn) {
        return Observable.error(new GetCommentFailException());
    }

    public Observable<T> getTopics(String letter) {
        return Observable.error(new GetTopicFailException());
    }

    protected boolean hasConnection() {
        return mNetworkUtil.isNetworkOn();
    }
}
