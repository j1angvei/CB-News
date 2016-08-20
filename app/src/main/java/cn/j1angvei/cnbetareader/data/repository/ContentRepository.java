package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.data.local.ContentLocalSource;
import cn.j1angvei.cnbetareader.data.remote.ContentRemoteSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentRepository implements Repository<Content> {
    private final ContentLocalSource mLocalSource;
    private final ContentRemoteSource mRemoteSource;

    @Inject
    public ContentRepository(ContentLocalSource localSource, ContentRemoteSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Content> getData(String sid, Map<String, String> param) {
        return mRemoteSource.getData(sid, param)
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        //to toRAM or toDisk to local disk
                        toDisk(content);
                    }
                });
    }

    @Override
    public void toDisk(Content item) {
        mLocalSource.create(item);
    }

    @Override
    public void toRAM(Content item) {

    }
}
