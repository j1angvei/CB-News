package cn.j1angvei.cnbetareader.data.remote.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wayne on 2016/6/13.
 */
public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request = originalRequest.newBuilder()
                .header("Accept", "*/*")
                .header("Referer", " http://www.cnbeta.com/")
                .header("X-Requested-With", " XMLHttpRequest")
                .header("Accept-Language", " zh-Hans,en-US;q=0.7,en;q=0.3")
                .header("Host", "www.cnbeta.com")
                .header("Connection", " Keep-Alive")
                .build();
        return chain.proceed(request);
    }
}
