package cn.j1angvei.cbnews.data.repository;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.data.local.ContentLocalSource;
import cn.j1angvei.cbnews.data.remote.ContentRemoteSource;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentRepository extends Repository<Content> {
    private final Map<String, Content> mContentMap;

    @Inject
    public ContentRepository(ContentLocalSource localSource, ContentRemoteSource remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mContentMap = new HashMap<>();
    }

    @Override
    public Observable<Content> getDataFromDB(@NonNull Integer page, @NonNull final String id, String typeOrSN) {
        return Observable.just(page == -1)
                .flatMap(new Func1<Boolean, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(Boolean refresh) {
                        Content content = mContentMap.get(id);
                        return refresh || content == null ? Observable.<Content>empty() : Observable.just(content);
                    }
                })
                .switchIfEmpty(mRemoteSource.fetchData(null, id))
                .switchIfEmpty(mLocalSource.read(null, id, null))
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        storeToMemory(content);
                        storeToDisk(content);
                    }
                });
    }

    @Override
    public Observable<Content> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public Observable<Content> getDataFromRAM(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public void storeToDisk(Content item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Content item) {
        if (mContentMap.containsKey(item.getSid())) {
            mContentMap.put(item.getSid(), item);
        }
    }
}
