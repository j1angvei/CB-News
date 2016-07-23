package cn.j1angvei.cnbetareader.data.remote;

import cn.j1angvei.cnbetareader.converter.Converter;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    final CnbetaApi mCnbetaApi;
    final Converter<T> mConverter;

    public RemoteSource(CnbetaApi api, Converter<T> converter) {
        mCnbetaApi = api;
        mConverter = converter;
    }

    public abstract Observable<T> getItem(int page, String... str);
}
