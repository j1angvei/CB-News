package cn.j1angvei.cbnews.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.util.ApiUtil;
import cn.j1angvei.cbnews.util.HeaderUtil;
import cn.j1angvei.cbnews.util.PrefsUtil;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Wayne on 2016/9/1.
 * wrapper of {@link CnbetaApi}
 */
@Singleton
public class CBApiWrapper {
    private static final String KEY_SID = "sid";
    private static final String KEY_CALLBACK = "jsoncallback";
    private static final String KEY_TYPE = "type";
    private static final String KEY_PAGE = "page";
    private static final String KEY_TIMESTAMP = "_";
    private static final String KEY_ID = "id";
    private static final String KEY_CSRF_TOKEN = "csrf_token";
    private static final String KEY_OP = "op";
    private static final String KEY_TID = "tid";
    private static final String KEY_PID = "pid";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_CAPTCHA = "seccode";
    private static final String KEY_REFRESH = "refresh";

    private CnbetaApi mCnbetaApi;
    private PrefsUtil mPrefsUtil;

    @Inject
    public CBApiWrapper(CnbetaApi cnbetaApi, PrefsUtil prefsUtil) {
        mCnbetaApi = cnbetaApi;
        mPrefsUtil = prefsUtil;
    }

    public Observable<ResponseBody> getNews(String type, int page) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CALLBACK, ApiUtil.getJsonp());
        map.put(KEY_TYPE, type);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return mCnbetaApi.getNews(map);
    }

    public Observable<ResponseBody> getTopicNews(String topicId, int page) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CALLBACK, ApiUtil.getJsonp());
        map.put(KEY_ID, topicId);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return mCnbetaApi.getTopicNews(map);
    }

    public Observable<ResponseBody> getComments(String sid, String sn) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        String op = ApiUtil.assembleCommentsOp(sid, sn);
        map.put(KEY_OP, op);
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.getArticleComment(referer, map);
    }

    public Observable<BaseResponse> judgeComment(String action, String sid, String tid) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_OP, action);
        map.put(KEY_SID, sid);
        map.put(KEY_TID, tid);
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.judgeComment(referer, map);
    }

    public Observable<PublishCmtResponse> publishComment(String content, String captcha, String sid, String pid) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        //action always to be publish
        String action = "publish";
        map.put(KEY_OP, action);
        map.put(KEY_CONTENT, content + mPrefsUtil.readCmtTail());
        map.put(KEY_CAPTCHA, captcha);
        map.put(KEY_SID, sid);
        map.put(KEY_PID, pid);
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.publishComment(referer, map);
    }

    public Observable<ResponseBody> getCaptchaVerify(String sid) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        //refresh are always "1"
        String refresh = "1";
        map.put(KEY_REFRESH, refresh);
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.getCaptchaVerify(referer, map);
    }

    public Observable<ResponseBody> getContent(String sid) {
        return mCnbetaApi.getArticleContent(sid);
    }

    public Observable<ResponseBody> getTopics(String letter) {
        return mCnbetaApi.getTopics(letter);
    }

    public Observable<ResponseBody> getCaptchaImage(String sid, String verify) {
        String referer = HeaderUtil.assembleRefererValue(sid);
        return mCnbetaApi.getCaptchaImage(referer, verify);
    }

    public Observable<ResponseBody> getCsrfToken() {
        return mCnbetaApi.getCsrfToken();
    }

    private String readToken() {
        return mPrefsUtil.readString(PrefsUtil.CSRF_TOKEN);
    }

}
