package cn.j1angvei.cnbetareader.converter;

import org.json.JSONException;

import java.util.List;

import rx.Observable;

/**
 * base converter, from response to bean
 * Created by Wayne on 2016/7/22.
 */
public interface Converter<F, T> {

    T to(F from) throws JSONException;

    List<T> toList(F from) throws JSONException;

    Observable<T> toObservable(F from);

}
