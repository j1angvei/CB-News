package cn.j1angvei.cnbetareader.converter;

import java.util.List;

import rx.Observable;

/**
 * base converter, from response to bean
 * Created by Wayne on 2016/7/22.
 */
public interface Converter<T> {

    T to(String json);

    List<T> toList(String json);

    Observable<T> toObservable(String json);

}
