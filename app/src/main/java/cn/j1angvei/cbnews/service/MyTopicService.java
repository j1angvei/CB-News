package cn.j1angvei.cbnews.service;

import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.base.BaseService;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.component.DaggerServiceComponent;
import cn.j1angvei.cbnews.di.module.ServiceModule;

/**
 * Created by Wayne on 2016/9/4.
 */

public class MyTopicService extends BaseService {
    private static final String TAG = "MyTopicService";
    private static final String TOPICS = "MyTopicService.topics";
    @Inject

    public MyTopicService() {
        super(TAG);
    }

    @Override
    protected void doInjection() {
        mServiceComponent = DaggerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .serviceModule(new ServiceModule(this))
                .build();
        mServiceComponent.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<Topic> topics = intent.getParcelableArrayListExtra(TOPICS);
    }
}
