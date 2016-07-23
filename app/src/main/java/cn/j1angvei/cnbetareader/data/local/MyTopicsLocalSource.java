package cn.j1angvei.cnbetareader.data.local;

import android.content.Context;

import cn.j1angvei.cnbetareader.bean.Article;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public class MyTopicsLocalSource extends LocalSource<Article> {

    public MyTopicsLocalSource(Context context) {
        super(context);
    }

    @Override
    Observable<Article> get() {
        return null;
    }

    @Override
    void save(Article item) {

    }
}
