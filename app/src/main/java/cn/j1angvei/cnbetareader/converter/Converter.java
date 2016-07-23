package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
public abstract class Converter<T> {
    protected Gson mGson;
    protected String mBaseUrl;

    public Converter(Gson gson, String baseUrl) {
        mGson = gson;
        mBaseUrl = baseUrl;
    }

    public abstract T to(String json);

    public abstract List<T> toList(String json);

    public abstract Observable<T> toObservable(String json);

    public List<Topic> toTopic(String html) {
        List<Topic> topics = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements dls = document.select("div.topic_list > div:eq(1) > dl");
        for (Element dl : dls) {
            Topic topic = new Topic();
            //cover
            topic.setCover(dl.select("dt > div > a > img").attr("src"));
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
    public Content toContent(String html) {
        Content content = new Content();

        Document doc = Jsoup.parse(html, mBaseUrl);
        //parse title
        String title = doc.getElementById("news_title").text();
        content.setTitle(title);
        //parse date
        String dateString = doc.getElementsByClass("date").first().text();
        try {
            Date date = DateUtil.toDate(dateString, DateUtil.DateFormatType.CNBETA);
            content.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //parse source
        String where = doc.getElementsByClass("where").first().text().replace("稿源：", "");
        content.setSource(where);
        //parse introduction
        String introduction = doc.select(".introduction > p").text();
        content.setSummary(introduction);
        //parse detail
        Element elementContent = doc.getElementsByClass("content").first();
        //convert relative url to absolute url
        for (Element e : elementContent.select("a[href]")) {
            e.attr("href", e.absUrl("href"));
        }
        String detail = elementContent.html();
        content.setDetail(detail);
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
        //parse token
        String token = "";
        pattern = Pattern.compile("TOKEN:\".+?\"");
        matcher = pattern.matcher(html);
        if (matcher.find()) {
            token = matcher.group();
            token = token.substring(token.indexOf('"') + 1, token.lastIndexOf('"'));
        }
        content.setToken(token);
        //parse topic image url
        String src = doc.select(".introduction > div > a > img").attr("src");
//        String src = doc.select(".introduction img").attr("src");
        content.setTopicPhoto(src);
        return content;
    }
    public Comments toComments(String json) {
        Comments comments = new Comments();
        try {
            JSONObject result = new JSONObject(json).getJSONObject("result");
            comments.setCommentNum(result.getString("comment_num"));
            comments.setJoinNum(result.getString("join_num"));
            comments.setOpen(result.getString("open").equals("1"));
            comments.setToken(result.getString("token"));
            comments.setPage(result.getString("page"));
            comments.setSid(result.getString("sid"));

            //hot comments tid list
            JSONArray hotArray = result.getJSONArray("hotlist");
            comments.setHotIds(getCommentIds(hotArray));

            //all comments tid list
            JSONArray allArray = result.getJSONArray("cmntlist");
            List<String> allIds = getCommentIds(allArray);
            comments.setAllIds(getCommentIds(allArray));

            //comments map
            JSONObject store = result.getJSONObject("cmntstore");
            Map<String, CommentItem> map = new HashMap<>();
            for (String tid : allIds) {
                CommentItem item = mGson.fromJson(store.getJSONObject(tid).toString(), CommentItem.class);
                map.put(tid, item);
            }
            comments.setCommentMap(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }

    private List<String> getCommentIds(JSONArray array) throws JSONException {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ids.add(object.getString("tid"));
        }
        return ids;
    }
}
