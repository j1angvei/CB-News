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

    public Observable<Boolean> operateComment(String... param) {
        String token = param[0], action = param[1], sid = param[2], tid = param[3];
        return mCnbetaApi.operateComment(token, action, sid, tid)
                .map(new Func1<BaseResponse, Boolean>() {
                    @Override
                    public Boolean call(BaseResponse baseResponse) {
                        return "success".equals(baseResponse.getState());
                    }
                });
    }

    public Observable<CommentResponse> publishComment(String... param) {
        String token = param[0], action = param[1], content = param[2], captcha = param[3], sid = param[4], pid = param[5];
        return mCnbetaApi.publishComment(token, action, content, captcha, sid, pid);
    }

}
