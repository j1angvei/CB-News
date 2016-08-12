package cn.j1angvei.cnbetareader.data.remote.api;

import java.util.Map;

import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import cn.j1angvei.cnbetareader.data.remote.response.CommentResponse;
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

import static cn.j1angvei.cnbetareader.util.ApiUtil.KEY_LETTER;
import static cn.j1angvei.cnbetareader.util.ApiUtil.KEY_SID;
import static cn.j1angvei.cnbetareader.util.ApiUtil.KEY_V;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_ACCEPT_IMG;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_ACCEPT_JSON;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_AJAX;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_CACHE_MAX;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_CACHE_NO;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.HEADER_PRAGMA;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_REFERER;

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
    Observable<ResponseBody> getTopics(@Query(KEY_LETTER) String letter);

    @Headers({HEADER_CACHE_NO, HEADER_PRAGMA})
    @GET("/articles/{sid}.htm")
    Observable<ResponseBody> getArticleContent(@Path(KEY_SID) String sid);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/cmt")
    Observable<ResponseBody> getArticleComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> commentsParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<BaseResponse> operateComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> operateCommentParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @FormUrlEncoded
    @POST("/comment")
    Observable<CommentResponse> publishComment(@Header(KEY_REFERER) String refer, @FieldMap Map<String, String> publishCommentParam);

    @Headers({HEADER_ACCEPT_JSON, HEADER_AJAX})
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaUrl(@Header(KEY_REFERER) String refer, @QueryMap Map<String, String> captchaUrlParam);

    @Headers(HEADER_ACCEPT_IMG)
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaImage(@Header(KEY_REFERER) String refer, @Query(KEY_V) String v);

    @GET("/deliver")
    Observable<ResponseBody> getCsrfToken();

}
