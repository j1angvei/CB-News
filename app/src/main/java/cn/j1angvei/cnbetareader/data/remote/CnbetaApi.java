package cn.j1angvei.cnbetareader.data.remote;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.RawHeadline;
import cn.j1angvei.cnbetareader.bean.RawReview;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.remote.response.ExposedResponse;
import cn.j1angvei.cnbetareader.data.remote.response.WrappedResponse;
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
 */
public interface CnbetaApi {

    @GET("/more")
    Observable<WrappedResponse<Article>> getArticles(@Query("jsoncallback") String callback, @Query("type") String type, @Query("page") int page, @Query("_") long timestamp);

    @GET("/more")
    Observable<ExposedResponse<RawReview>> getReviews(@Query("jsoncallback") String callback, @Query("type") String type, @Query("page") int page, @Query("_") long timestamp);

    @GET("/more")
    Observable<ExposedResponse<RawHeadline>> getHeadlines(@Query("jsoncallback") String callback, @Query("type") String type, @Query("page") int page, @Query("_") long timestamp);

    @GET("/topics/more")
    Observable<WrappedResponse<Article>> getTopicArticles(@Query("jsoncallback") String callback, @Query("id") String id, @Query("page") int page, @Query("_") long timestamp);

    @GET("/articles/{sid}.htm")
    Observable<ResponseBody> getArticleContent(@Path("sid") String sid);

    @FormUrlEncoded
    @POST("/cmt")
    Observable<ResponseBody> getArticleComment(@Field("csrf_token") String token, @Field("op") String op);

}
