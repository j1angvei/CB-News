package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.ServiceModule;
import cn.j1angvei.cnbetareader.di.scope.PerService;
import cn.j1angvei.cnbetareader.service.OfflineDownloadService;
import dagger.Component;

/**
 * Created by Wayne on 2016/9/1.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(OfflineDownloadService service);
}
