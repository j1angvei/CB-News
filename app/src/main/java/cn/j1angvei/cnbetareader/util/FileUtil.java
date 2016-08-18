package cn.j1angvei.cnbetareader.util;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/8/15.
 */
@Singleton
public class FileUtil {
    private Context mContext;

    @Inject
    public FileUtil(Application application) {
        mContext = application;
    }

    public void writeTopicIds(List<String> topicIds) {

    }
}
