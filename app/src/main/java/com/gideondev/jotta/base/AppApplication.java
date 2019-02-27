package com.gideondev.jotta.base;

import android.app.Application;
import com.gideondev.jotta.internal.di.component.ApplicationComponent;
import com.gideondev.jotta.internal.di.component.DaggerApplicationComponent;
import com.gideondev.jotta.internal.di.module.ApplicationModule;
import javax.inject.Inject;

/**
 * Created by Enny on 29/11/2016.
 */

public class AppApplication
    extends Application {
    private static AppApplication application;
    private ApplicationComponent mComponent;
    @Inject
//    public ContactDAO mContactDao;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initApplication();
    //    AppEventsLogger.activateApp(this);
    }


    private void initApplication() {
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mComponent.inject(this);
    }


    public static AppApplication get() {
        return application;
    }

}
