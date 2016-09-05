package cn.j1angvei.cbnews.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.data.local.ContentLocalSource;
import cn.j1angvei.cbnews.data.remote.ContentRemoteSource;
import cn.j1angvei.cbnews.util.NetworkUtil;
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
    public Observable<Content> getData(int page, String id, String typeOrSN) {
        return isConnected() ?
                mRemoteSource.loadData(0, id)
                        .doOnNext(new Action1<Content>() {
                            @Override
                            public void call(Content content) {
                                storeToDisk(content);
                            }
                        }) :
                mLocalSource.read(0, id, null);
    }

    @Override
    public void storeToDisk(Content item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Content item) {

    }
}
