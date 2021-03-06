package cn.j1angvei.cbnews.web;

import java.util.Map;

import cn.j1angvei.cbnews.web.BaseResponse;
import cn.j1angvei.cbnews.web.PublishCmtResponse;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_ACCEPT_IMG;
import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_ACCEPT_JSON;
import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_AJAX;
import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_CACHE_MAX;
import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_CACHE_NO;
import static cn.j1angvei.cbnews.util.HeaderUtil.HEADER_PRAGMA;
import static cn.j1angvei.cbnews.util.HeaderUtil.KEY_REFERER;

/**
 * Created by Wayne on 2016/6/13.
 * cnBeta Api retrieved from fiddler
 */
public interface CnbetaApi {
    @Headers(HEADER_AJAX)
    @GET("/more")
    Observable<ResponseBody> getNews(@QueryMap Map<String, String> newsParam);

    @Headers(HEADER_AJAX)
    @GET("/topics/more")
    Observable<ResponseBody> getTopicNews(@QueryMap Map<String, String> topicNewsParam);

    @Headers(HEADER_CACHE_MAX)
    @GET("/topics.htm")
    Observable<ResponseBody> getTopics(@Query("letter") String letter);

    @Headers({HEADER_CACHE_NO, HEADER_PRAGMA})
    @GET("/articles/{sid}.htm")
    Observable<ResponseBody> getArticleContent(@Path("sid") String sid);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/cmt")
    Observable<ResponseBody> getArticleComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> commentsParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<BaseResponse> judgeComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> judgeCommentParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<PublishCmtResponse> publishComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> publishCommentParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaVerify(@Header(KEY_REFERER) String refer, @QueryMap Map<String, String> captchaUrlParam);

    @Headers(HEADER_ACCEPT_IMG)
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaImage(@Header(KEY_REFERER) String refer, @Query("v") String verify);

    @GET("/deliver")
    Observable<ResponseBody> getCsrfToken();

}
