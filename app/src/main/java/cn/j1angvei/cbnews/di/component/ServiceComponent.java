package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.ServiceModule;
import cn.j1angvei.cbnews.di.scope.PerService;
import cn.j1angvei.cbnews.service.MyTopicService;
import cn.j1angvei.cbnews.service.OfflineDownloadService;
import dagger.Component;

/**
 * Created by Wayne on 2016/9/1.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(OfflineDownloadService service);

    void inject(MyTopicService service);
}
