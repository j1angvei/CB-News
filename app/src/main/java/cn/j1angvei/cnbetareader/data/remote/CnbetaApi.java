package cn.j1angvei.cnbetareader.data.remote;

import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import cn.j1angvei.cnbetareader.data.remote.response.CommentResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_ACCEPT_IMG;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_ACCEPT_JSON;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_AJAX;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_CACHE_MAX;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_CACHE_NO;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_REFERER;

/**
 * Created by Wayne on 2016/6/13.
 * cnBeta Api retrieved from fiddler
 */
public interface CnbetaApi {
    @Headers(HEADER_AJAX)
    @GET("/more")
    Observable<ResponseBody> getNews(@Query("jsoncallback") String callback, @Query("type") String type, @Query("page") int page, @Query("_") long timestamp);

    @Headers(HEADER_AJAX)
    @GET("/topics/more")
    Observable<ResponseBody> getTopicNews(@Query("jsoncallback") String callback, @Query("id") String topicId, @Query("page") int page, @Query("_") long timestamp);

    @Headers(HEADER_CACHE_MAX)
    @GET("/topics.htm")
    Observable<ResponseBody> getTopics(@Query("letter") String letter);

    @Headers({HEADER_CACHE_NO, "Pragma:no-cache"})
    @GET("/articles/{sid}.htm")
    Observable<ResponseBody> getArticleContent(@Path("sid") String sid);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/cmt")
    Observable<ResponseBody> getArticleComment(@Header(KEY_REFERER) String refer, @Field("csrf_token") String token, @Field("op") String op);//OP format, "1,sid,sn"

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<BaseResponse> operateComment(@Header(KEY_REFERER) String refer, @Field("csrf_token") String token, @Field("op") String action, @Field("sid") String sid, @Field("tid") String tid);// op as "support against report"

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<CommentResponse> publishComment(@Header(KEY_REFERER) String refer, @Field("csrf_token") String token, @Field("op") String action, @Field("content") String content, @Field("seccode") String captcha, @Field("sid") String sid, @Field("pid") String pid);//op as "publish"

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaAddress(@Header(KEY_REFERER) String refer, @Query("refresh") String refresh, @Query("csrf_token") String token, @Query("_") long timestamp);

    @Headers(HEADER_ACCEPT_IMG)
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaImage(@Header(KEY_REFERER) String refer, @Query("v") String v);

    @GET("/deliver")
    Observable<ResponseBody> getCsrfToken();

}
