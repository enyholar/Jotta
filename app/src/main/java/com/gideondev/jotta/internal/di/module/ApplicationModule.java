package com.gideondev.jotta.internal.di.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.gideondev.jotta.base.AppApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Enny on 29/11/2016.
 */
@Module
public class ApplicationModule {
    private AppApplication mApp;

    public ApplicationModule(AppApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }

}