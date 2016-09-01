package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.data.local.CommentsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.CommentsRemoteSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsRepository extends Repository<Comments> {
    private final CommentsLocalSource mLocalSource;
    private final CommentsRemoteSource mRemoteSource;

    @Inject
    public CommentsRepository(CommentsLocalSource localSource, CommentsRemoteSource remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Comments> getData(String id, String param, int page) {//param is "SN" here
        return isConnected() ?
                mRemoteSource.loadData(page, id, param)
                        .doOnNext(new Action1<Comments>() {
                            @Override
                            public void call(Comments comments) {
                                storeToDisk(comments);
                            }
                        }) :
                mLocalSource.read(id, null, page);
    }

    @Override
    public void storeToDisk(Comments item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Comments item) {
    }

}
