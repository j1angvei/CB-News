package cn.j1angvei.cbnews.converter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.CommentItem;
import cn.j1angvei.cbnews.bean.Comments;
import rx.Observable;

/**
 * convert response to comments
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class CommentsConverter implements Converter<String, Comments> {
    private static final String TAG = "CommentsConverter";
    final private Gson mGson;

    @Inject
    public CommentsConverter(Gson gson) {
        mGson = gson;
    }

    @Override
    public Comments to(String json) {
        Comments comments;
        JSONObject result;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String state = jsonObject.getString("state");
            if (!TextUtils.equals(state, "success")) {
                return null;
            }
            comments = new Comments();
            result = jsonObject.getJSONObject("result");

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
            if (comments.isOpen() && comments.getAllIds().size() > 0) {
                JSONObject store = result.getJSONObject("cmntstore");
                Map<String, CommentItem> map = new HashMap<>();
                for (String tid : allIds) {
                    CommentItem item = mGson.fromJson(store.getJSONObject(tid).toString(), CommentItem.class);
                    map.put(tid, item);
                }
                comments.setCommentMap(map);
            }
        } catch (JSONException e) {
            Log.d(TAG, "to: " + json);
            e.printStackTrace();
            return null;
        }
        return comments;
    }

    @Override
    public List<Comments> toList(String json) {
        return null;
    }

    @Override
    public Observable<Comments> toObservable(String json) {
        Comments comments = to(json);
        if (comments == null) {
            return Observable.empty();
        }
        return Observable.just(comments);
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
