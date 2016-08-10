package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.data.local.CommentsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.CommentsRemoteSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsRepository implements Repository<Comments> {
    private final CommentsLocalSource mLocalSource;
    private final CommentsRemoteSource mRemoteSource;

    @Inject
    public CommentsRepository(CommentsLocalSource localSource, CommentsRemoteSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Comments> getData(String referer, Map<String, String> param) {
        return mRemoteSource.getData(referer, param)
                .doOnNext(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {
                        //toDisk to toRAM or store to local disk
                    }
                });
    }

    @Override
    public void toDisk(Comments item) {

    }

    @Override
    public void toRAM(Comments item) {

    }

}
