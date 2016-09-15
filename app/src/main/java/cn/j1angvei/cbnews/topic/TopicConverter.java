package cn.j1angvei.cbnews.topic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * convert response to Topic
 */
@Singleton
public class TopicConverter implements Converter<Topic> {
    @Inject
    public TopicConverter() {
    }

    @Override
    public Topic to(String json) {
        return null;
    }

    @Override
    public List<Topic> toList(String json) {
        List<Topic> topics = new ArrayList<>();
        Document document = Jsoup.parse(json);
        Elements dls = document.select("div.topic_list > div:eq(1) > dl");
        for (Element dl : dls) {
            Topic topic = new Topic();
            //cover
            topic.setThumb(dl.select("dt > div > a > img").attr("src"));
            //elements contain id and title
            Element a = dl.select("dd > a").get(0);
            //title
            topic.setTitle(a.text());
            //id
            String href = a.attr("href");
            topic.setId(href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf('.')));
            topics.add(topic);
        }
        return topics;
    }

    @Override
    public Observable<Topic> toObservable(String json) {
        List<Topic> topics = toList(json);
        return topics == null?
                Observable.<Topic>empty():
        Observable.from(topics);
    }
}
