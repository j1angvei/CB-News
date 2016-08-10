package cn.j1angvei.cnbetareader.data.remote;

import java.util.Map;

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

    public abstract Observable<T> getData(String extra, Map<String, String> param);
}
