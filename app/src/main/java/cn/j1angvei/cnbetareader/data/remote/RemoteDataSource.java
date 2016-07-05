package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.RawReview;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.DataSource;
import cn.j1angvei.cnbetareader.data.remote.response.ExposedResponse;
import cn.j1angvei.cnbetareader.data.remote.response.WrappedResponse;
import cn.j1angvei.cnbetareader.util.BeanConverter;
import cn.j1angvei.cnbetareader.util.JsonpGenerator;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/6/16.
 */
@Singleton
public class RemoteDataSource implements DataSource {
    private CnbetaApi mCnbetaApi;
    private BeanConverter mConverter;

    @Inject
    public RemoteDataSource(CnbetaApi api, BeanConverter converter) {
        mCnbetaApi = api;
        mConverter = converter;
    }

    @Override
    public Observable<Article> getArticleFromSource(String type, int page) {
        String callback = JsonpGenerator.getParameter();
        long timeStamp = JsonpGenerator.getInitTime() + page;

        return mCnbetaApi.getArticles(callback, type, page, timeStamp)
                .map(new Func1<WrappedResponse<Article>, List<Article>>() {
                    @Override
                    public List<Article> call(WrappedResponse<Article> articleWrappedResponse) {
                        return articleWrappedResponse.getResult().getList();
                    }
                })
                .flatMap(new Func1<List<Article>, Observable<Article>>() {
                    @Override
                    public Observable<Article> call(List<Article> articles) {
                        return Observable.from(articles);
                    }
                });
    }

    @Override
    public Observable<Content> getContentFromSource(String sid) {
        return mCnbetaApi.getArticleContent(sid).map(new Func1<ResponseBody, Content>() {
            @Override
            public Content call(ResponseBody responseBody) {
                Content content = null;
                try {
                    content = mConverter.toContent(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return content;
            }
        });
    }

    @Override
    public Observable<Review> getReviewsFromSource(String type, int page) {
        String callback = JsonpGenerator.getParameter();
        long timeStamp = JsonpGenerator.getInitTime() + page;
        return mCnbetaApi.getReviews(callback, type, page, timeStamp)
                .flatMap(new Func1<ExposedResponse<RawReview>, Observable<RawReview>>() {
                    @Override
                    public Observable<RawReview> call(ExposedResponse<RawReview> rawReviewExposedResponse) {
                        return Observable.from(rawReviewExposedResponse.getResult());
                    }
                })
                .map(new Func1<RawReview, Review>() {
                    @Override
                    public Review call(RawReview rawReview) {
                        return mConverter.toReview(rawReview);
                    }
                });
    }
}
