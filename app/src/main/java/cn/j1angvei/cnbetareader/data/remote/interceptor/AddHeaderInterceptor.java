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
                .header("Cookie", "Hm_lvt_4216c57ef1855492a9281acd553f8a6e=1470659126,1470714170,1470749856,1470756082; _ga=GA1.2.971540717.1469274968; _gat=1; Hm_lpvt_4216c57ef1855492a9281acd553f8a6e=1470757470; csrf_token=6492909442aeae1a9f6cec6d7eb2f241b4d66b54; PHPSESSID=5rshlvpltd2auk0pfa0kmfs1q6")
                .header("X-Requested-With", " XMLHttpRequest")
                .header("Accept-Language", " zh-Hans,en-US;q=0.7,en;q=0.3")
                .header("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36 Edge/14.14342")
                .header("Host", "www.cnbeta.com")
                .header("Connection", " Keep-Alive")
                .build();
        return chain.proceed(request);
    }
}
