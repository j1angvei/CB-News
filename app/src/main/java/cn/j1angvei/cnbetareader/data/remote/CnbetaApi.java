package cn.j1angvei.cnbetareader.data.remote;


import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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
    Observable<BaseResponse> actOnComment(@Field("csrf_token") String token, @Field("op") String op, @Field("sid") String sid, @Field("tid") String tid);// op as "support against report reply"

    @FormUrlEncoded
    @POST("/comment")
    Observable<BaseResponse> publishComment(@Field("csrf_token") String token, @Field("op") String op, @Field("content") String content, @Field("seccode") String captcha, @Field("sid") String sid, @Field("pid") String pid);//op as "publish", pid should be '0'

    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaAddress(@Query("refresh") String refresh, @Query("csrf_token") String token, @Query("_") long timestamp);

    //    @Headers("Accept: image/png, image/svg+xml, image/jxr, image/*;q=0.8, */*;q=0.5")
    @GET("/captcha.htm")
    Observable<ResponseBody> getCaptchaImage(@Header("Accept") String headerImg, @Header("Referer") String referer, @Query("v") String v);

}
