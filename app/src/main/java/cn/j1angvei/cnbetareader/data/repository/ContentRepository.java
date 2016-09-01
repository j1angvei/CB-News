package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.data.local.ContentLocalSource;
import cn.j1angvei.cnbetareader.data.remote.ContentRemoteSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentRepository extends Repository<Content> {
    private final ContentLocalSource mLocalSource;
    private final ContentRemoteSource mRemoteSource;

    @Inject
    public ContentRepository(ContentLocalSource localSource, ContentRemoteSource remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Content> getData(String id, String param, int page) {// only need id here
        return isConnected() ?
                mRemoteSource.loadData(0, id)
                        .doOnNext(new Action1<Content>() {
                            @Override
                            public void call(Content content) {
                                storeToDisk(content);
                            }
                        }) :
                mLocalSource.read(id, null, 0);
    }

    @Override
    public void storeToDisk(Content item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Content item) {

    }
}
