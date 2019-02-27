package com.gideondev.jotta.internal.di.component;

import com.gideondev.jotta.base.AppApplication;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.BaseFragment;
import com.gideondev.jotta.internal.di.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Enny on 29/11/2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(AppApplication app);
    void inject(BaseActionbarActivity activity);
    void inject(BaseFragment activity);

//    void inject(LocalDataSource localDataSource);
}
