package cn.j1angvei.cnbetareader.data.remote;


import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Observable<ResponseBody> getArticleComment(@Field("csrf_token") String token, @Field("op") String op);


}
