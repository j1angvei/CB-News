package cn.j1angvei.cnbetareader.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.StringUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public class ContentConverter implements Converter<Content> {

    @Override
    public Content to(String html) {
        Content content = new Content();

        Document doc = Jsoup.parse(html, DateUtil.DATE_FORMAT_CB);
        //parse title
        String title = doc.getElementById("news_title").text();
        content.setTitle(StringUtil.removeBlanks(title));
        //parse date
        String dateString = doc.getElementsByClass("date").first().text();
        try {
            Date date = DateUtil.toDate(dateString, DateUtil.DATE_FORMAT_CB);
            content.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //parse source
        String where = doc.getElementsByClass("where").first().text().replace("稿源：", "");
        content.setSource(where);
        //parse introduction
        String introduction = doc.select(".introduction > p").text();
        content.setSummary(StringUtil.removeBlanks(introduction));
        //parse detail
        Element elementContent = doc.getElementsByClass("content").first();
        //convert relative url to absolute url
        for (Element e : elementContent.select("a[href]")) {
            e.attr("href", e.absUrl("href"));
        }
        //modify width to fit device width
        elementContent.select("[width]").attr("width", "100%");
        //remove height
        elementContent.select("[height]").removeAttr("height");
        //make image to fit device width
        elementContent.select("img").attr("width", "100%");
        //generate new element to wrap content
        Element container = new Element(Tag.valueOf("html"), "");
        //viewport makes content adjusts to content width,justify style makes content justify
        container.html("<head><meta name=\"viewport\" content=\"width=device-width,user-scalable=no\"></head><body style=\"text-align:justify\"></body>");
        //modify width in javascript to 100%
        String detail = elementContent.html().replaceAll("\"width\":\"\\d+\"", "\"width\":\"100%\"");
        container.getElementsByTag("body").first().html(detail);
        content.setDetail(StringUtil.removeTailingBlanks(container.outerHtml()));
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
        content.setTopicPhoto(src);
        return content;
    }

    @Override
    public List<Content> toList(String json) {
        return null;
    }

    @Override
    public Observable<Content> toObservable(String json) {
        return Observable.just(to(json));
    }
}
