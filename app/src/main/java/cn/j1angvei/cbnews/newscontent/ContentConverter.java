package cn.j1angvei.cbnews.newscontent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.util.ApiUtil;
import cn.j1angvei.cbnews.util.DateUtil;
import cn.j1angvei.cbnews.util.StringUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class ContentConverter implements Converter<Content> {
    private static final String TAG = "ContentConverter";

    @Inject
    public ContentConverter() {
    }

    @Override
    public Content to(String html) {
        Content content;
        try {
            content = new Content();
            Document doc = Jsoup.parse(html);
            //parse title
            String title = doc.getElementById("news_title").text();
            content.setTitle(StringUtil.removeBlanks(title));
            //parse date
            String dateString = doc.getElementsByClass("date").first().text();
            if (dateString != null) {
                Date date = DateUtil.toDate(dateString, DateUtil.DATE_FORMAT_CB);
                content.setTime(date);
            } else {
                return null;
            }
            //parse source
            String where = doc.getElementsByClass("where").first().text().replace("稿源：", "");
            content.setSource(ApiUtil.removeAtChar(where));
            //parse introduction
            String introduction = doc.select(".introduction > p").text();
            content.setSummary(StringUtil.removeBlanks(introduction));
            //parse detail
            String detail = doc.getElementsByClass("content").first().outerHtml();
            content.setDetail(StringUtil.removeTailingBlanks(detail));
            //parse sid, sn and token
            Pattern pattern;
            Matcher matcher;
            //parse sid
            String sid = "";
            pattern = Pattern.compile("SID:\".+?\"");
            matcher = pattern.matcher(html);
            if (matcher.find()) {
                sid = matcher.group();
                sid = sid.substring(sid.indexOf('"') + 1, sid.lastIndexOf('"'));
            }
            content.setSid(sid);
            //parse sn
            String sn = "";
            pattern = Pattern.compile("SN:\".+?\"");
            matcher = pattern.matcher(html);
            if (matcher.find()) {
                sn = matcher.group();
                sn = sn.substring(sn.indexOf('"') + 1, sn.lastIndexOf('"'));
            }
            content.setSn(sn);
            //parse topic image url
            String src = doc.select(".introduction > div > a > img").attr("src");
            content.setThumb(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return content;
    }

    @Override
    public List<Content> toList(String json) {
        return null;
    }

    @Override
    public Observable<Content> toObservable(String json) {
        Content content = to(json);
        if (content == null) {
            return Observable.empty();
        }
        return Observable.just(content);
    }
}
