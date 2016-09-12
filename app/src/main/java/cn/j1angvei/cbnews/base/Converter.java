package cn.j1angvei.cbnews.base;

import java.util.List;

import rx.Observable;

/**
 * base converter, from response to bean
 * Created by Wayne on 2016/7/22.
 */
public interface Converter<T> {

    T to(String from);

    List<T> toList(String from);

    Observable<T> toObservable(String from);

}
