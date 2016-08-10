package cn.j1angvei.cnbetareader.data.remote.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_ACCEPT;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_CONNECTION;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_HOST;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_LANGUAGE;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_REFERER;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.KEY_UA;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_ACCEPT;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_CONNECTION;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_HOST;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_LANGUAGE;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_REFER;
import static cn.j1angvei.cnbetareader.util.HeaderUtil.VALUE_UA;

/**
 * Created by Wayne on 2016/6/13.
 * add headers in request
 */
public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        //if headers not added in Api interface, then add them here.
        if (originalRequest.header(KEY_REFERER) == null) {
            builder.header(KEY_REFERER, VALUE_REFER);
        }
        if (originalRequest.header(KEY_ACCEPT) == null) {
            builder.header(KEY_ACCEPT, VALUE_ACCEPT);
        }
        //add basic headers
        builder.header(KEY_LANGUAGE, VALUE_LANGUAGE)
                .header(KEY_CONNECTION, VALUE_CONNECTION)
                .header(KEY_HOST, VALUE_HOST)
                .header(KEY_UA, VALUE_UA);
        return chain.proceed(builder.build());
    }
}
