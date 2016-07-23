package cn.j1angvei.cnbetareader.data.remote;

import java.io.IOException;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.converter.Converter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class CommentsRemoteSource extends RemoteSource<Comments> {
    public CommentsRemoteSource(CnbetaApi api, Converter<Comments> converter) {
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
}
