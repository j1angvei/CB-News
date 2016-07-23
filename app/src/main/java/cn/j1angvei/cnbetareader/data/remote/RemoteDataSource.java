package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;
import java.util.List;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.data.DataSource;
import cn.j1angvei.cnbetareader.data.remote.response.WrappedResponse;
import cn.j1angvei.cnbetareader.util.JsonpUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/6/16.
 */
public class RemoteDataSource<T> implements DataSource<T> {
    private CnbetaApi mCnbetaApi;
    private Converter<T> mConverter;

    public RemoteDataSource(CnbetaApi api, Converter<T> converter) {
        mCnbetaApi = api;
        mConverter = converter;
    }

    @Override
    public Observable<T> getNews(String type, int page) {
        String callback = JsonpUtil.getParameter();
        long timeStamp = JsonpUtil.getInitTime() + page;
        return mCnbetaApi.getNews(callback, type, page, timeStamp)
                .flatMap(new Func1<ResponseBody, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    @Override
    public Observable<Content> getContent(String sid) {
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
    public Observable<Comments> getComments(String token, String op) {
        return null;
    }

    @Override
    public Observable<Article> getTopicArticles(String topicId, int page) {
        String callback = JsonpUtil.getParameter();
        long timeStamp = JsonpUtil.getInitTime() + page;
        return mCnbetaApi.getTopicNews(callback, topicId, page, timeStamp)
                .map(new Func1<WrappedResponse<Article>, List<Article>>() {
                    @Override
                    public List<Article> call(WrappedResponse<Article> articleWrappedResponse) {
                        return articleWrappedResponse.getResult().getList();
                    }
                }).flatMap(new Func1<List<Article>, Observable<Article>>() {
                    @Override
                    public Observable<Article> call(List<Article> articles) {
                        return Observable.from(articles);
                    }
                });
    }

    @Override
    public Observable<Topic> getTopics(char letter) {
        return mCnbetaApi.getTopics(letter)
                .map(new Func1<ResponseBody, List<Topic>>() {
                    @Override
                    public List<Topic> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toTopic(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .flatMap(new Func1<List<Topic>, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(List<Topic> topics) {
                        return Observable.from(topics);
                    }
                });
    }
}
