package cn.j1angvei.cnbetareader.data.remote.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * jsonp response is not valid json string, using this interceptor to remove redundant string
 * Created by Wayne on 2016/6/13.
 */
public class JsonpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String jsonp = response.body().string();
        String json = jsonp.substring(jsonp.indexOf('{'), jsonp.lastIndexOf('}') + 1);
        MediaType contentType = response.body().contentType();
        ResponseBody body = ResponseBody.create(contentType, json);
        return response.newBuilder().body(body).build();
    }
}
