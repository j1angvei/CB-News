package cn.j1angvei.cbnews.data.repository;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.data.local.CommentsLocalSource;
import cn.j1angvei.cbnews.data.remote.CommentsRemoteSource;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsRepository extends Repository<Comments> {
    private final CommentsLocalSource mLocalSource;
    private final CommentsRemoteSource mRemoteSource;
    private final Map<String, Comments> mCommentsMap;

    @Inject
    public CommentsRepository(CommentsLocalSource localSource, CommentsRemoteSource remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mCommentsMap = new HashMap<>();
    }

    @Override
    public Observable<Comments> getDataFromDB(@NonNull Integer page, @NonNull final String id, @NonNull String typeOrSN) {
        if (page < 0) {
            Comments comments = mCommentsMap.get(id);
            return comments == null ? Observable.<Comments>error(new RAMItemNotFoundException()) : Observable.just(comments);
        } else
            return mRemoteSource.fetchData(null, id, typeOrSN)
                    .doOnNext(new Action1<Comments>() {
                        @Override
                        public void call(Comments comments) {
                            storeToDisk(comments);
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends Comments>>() {
                        @Override
                        public Observable<? extends Comments> call(Throwable throwable) {
                            return mLocalSource.read(null, id, null);
                        }
                    })
                    .doOnNext(new Action1<Comments>() {
                        @Override
                        public void call(Comments comments) {
                            storeToMemory(comments);
                        }
                    });
    }

    @Override
    public Observable<Comments> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public void storeToDisk(Comments item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Comments item) {
        mCommentsMap.put(item.getSid(), item);
    }

}
