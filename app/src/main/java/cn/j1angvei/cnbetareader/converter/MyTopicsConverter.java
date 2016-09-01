package cn.j1angvei.cnbetareader.converter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/9/1.
 */
@Singleton
public class MyTopicsConverter implements Converter<Topic, MyTopic> {
    @Inject
    public MyTopicsConverter() {
    }

    @Override
    public MyTopic to(Topic from) {
        MyTopic myTopic = new MyTopic();
        myTopic.setPage(from.getPage());
        myTopic.setId(from.getId());
        myTopic.setThumb(from.getThumb());
        myTopic.setTitle(from.getTitle());
        return myTopic;
    }

    @Override
    public List<MyTopic> toList(Topic from) {
        return null;
    }

    @Override
    public Observable<MyTopic> toObservable(Topic from) {
        return Observable.just(to(from));
    }
}
