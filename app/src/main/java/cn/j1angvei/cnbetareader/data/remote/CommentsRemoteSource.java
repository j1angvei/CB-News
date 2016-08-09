package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import cn.j1angvei.cnbetareader.data.remote.response.CommentResponse;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class CommentsRemoteSource extends RemoteSource<Comments> {
    @Inject
    public CommentsRemoteSource(CnbetaApi api, @Named("c_comments") Converter<Comments> converter) {
        super(api, converter);
    }

    @Override
    public Observable<Comments> getItem(int page, String... str) {
        String token = str[0];
        String op = str[1];
        return mCnbetaApi.getArticleComment(token, op)
                .flatMap(new Func1<ResponseBody, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(ResponseBody responseBody) {
                        try {
                            return mConverter.toObservable(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    public Observable<Boolean> operateComment(String token, String op, String sid, String tid) {
        return mCnbetaApi.actOnComment(token, op, sid, tid)
                .map(new Func1<BaseResponse, Boolean>() {
                    @Override
                    public Boolean call(BaseResponse baseResponse) {
                        return "success".equals(baseResponse.getState());
                    }
                });
    }

    public Observable<CommentResponse> publishComment(String token, String op, String content, String captcha, String sid, String pid) {
        return mCnbetaApi.publishComment(token, op, content, captcha, sid, pid);
    }

}
