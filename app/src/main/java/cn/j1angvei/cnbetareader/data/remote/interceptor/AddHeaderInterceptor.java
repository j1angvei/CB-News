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
        Request.Builder builder = originalRequest.newBuilder();
        //if not retrieve captcha, add accept and referer header
        if (originalRequest.header("Accept") == null) {
            builder.header("Accept", " text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
        }
        if (originalRequest.header("Referer") == null) {
            builder.header("Referer", " http://www.cnbeta.com/");
        }
        //normal header
        builder.header("X-Requested-With", " XMLHttpRequest")
                .header("Accept-Language", " zh-Hans,en-US;q=0.7,en;q=0.3")
//                .header("Accept-Encoding", " gzip, deflate")
                .header("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36 Edge/14.14342")
                .header("Host", "www.cnbeta.com")
                .header("Connection", " Keep-Alive")
                .build();
        return chain.proceed(builder.build());
    }
}
