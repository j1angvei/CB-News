package cn.j1angvei.cbnews.converter;

import java.util.List;

import rx.Observable;

/**
 * base converter, from response to bean
 * Created by Wayne on 2016/7/22.
 */
public interface Converter<F, T> {

    T to(F from);

    List<T> toList(F from);

    Observable<T> toObservable(F from);

}
