package cn.j1angvei.cnbetareader.data.remote;


import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import cn.j1angvei.cnbetareader.data.remote.response.CommentResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Wayne on 2016/6/13.
 * cnBeta Api retrieved from fiddler
 */
public interface CnbetaApi {
    @GET("/more")
    Observable<ResponseBody> getNews(@Query("jsoncallback") String callback, @Query("type") String type, @Query("page") int page, @Query("_") long timestamp);

    @GET("/topics/more")
    Observable<ResponseBody> getTopicNews(@Query("jsoncallback") String callback, @Query("id") String topicId, @Query("page") int page, @Query("_") long timestamp);

    @GET("/topics.htm")
    Observable<ResponseBody> getTopics(@Query("letter") String letter);

    @GET("/articles/{sid}.htm")
    Observable<ResponseBody> getArticleContent(@Path("sid") String sid);

    @FormUrlEncoded
    @POST("/cmt")
    Observable<ResponseBody> getArticleComment(@Field("csrf_token") String token, @Field("op") String op);//OP format, "1,sid,sn"

    @FormUrlEncoded
    @POST("/comment")
    Observable<BaseResponse> operateComment(@Field("csrf_token") String token, @Field("op") String action, @Field("sid") String sid, @Field("tid") String tid);// op as "support against report"

    @FormUrlEncoded
    @POST("/comment")
    Observable<CommentResponse> publishComment(@Field("csrf_token") String token, @Field("op") String action, @Field("content") String content, @Field("seccode") String captcha, @Field("sid") String sid, @Field("pid") String pid);//op as "publish"

    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaAddress(@Query("refresh") String refresh, @Query("csrf_token") String token, @Query("_") long timestamp);

    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaImage(@Query("v") String v);

}
