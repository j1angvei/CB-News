package cn.j1angvei.cnbetareader.data.repository;

import android.util.Log;

import java.util.Map;

import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 * store news like Article Headline Review into SQLiteDatabase
 */
public class NewsRepository<T> extends Repository<T> {
    private static final String TAG = "NewsRepository";
    private final NewsLocalSource<T> mLocalSource;
    private final NewsRemoteSource<T> mRemoteSource;

    public NewsRepository(NewsLocalSource<T> localSource, NewsRemoteSource<T> remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<T> getData(String extra, Map<String, String> param) {
        if (mInitLoad) {
            Log.d(TAG, "getData: init load");
            mInitLoad = false;
            return mLocalSource.read();
        } else if (!connected()) {
            Log.d(TAG, "getData: no connection");
            return Observable.empty();
        } else return mRemoteSource.getData(extra, param)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
//                        toDisk(t);
                    }
                });
    }

    @Override
    public void toDisk(T item) {
    }

    @Override
    public void toRAM(T item) {
    }
}
