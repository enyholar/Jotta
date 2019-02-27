package com.gideondev.jotta.internal.di.module;

import android.app.Activity;
import com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl.UserLoginPresenterImpl;
import com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl.UserResetPasswordPresenterImpl;
import com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl.UserSignUpPresenterImpl;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserLoginPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserResetPasswordPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserSignUpPresenter;
import com.gideondev.jotta.feature.main.presenter.presenterImpl.MainPresenterImpl;
import com.gideondev.jotta.feature.main.presenter.view.MainPresenter;
import com.gideondev.jotta.feature.noteInput.presenter.presenterImpl.NoteInputPresenterImpl;
import com.gideondev.jotta.feature.noteInput.presenter.view.NoteInputPresenter;
import com.gideondev.jotta.feature.Settings.presenter.presenterImpl.SettingsPresenterImpl;
import com.gideondev.jotta.feature.Settings.presenter.view.SettingsPresenter;
import com.gideondev.jotta.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Enny  on 29/11/2016.
 */
@Module(includes = ApplicationModule.class)
public class ProjectModule {
    private final Activity activity;

    public ProjectModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @Provides
    MainPresenter provideMainPresenter(){
        return new MainPresenterImpl();
    }

    @Provides
    NoteInputPresenter  provideNoteInputPresenter(){
        return new NoteInputPresenterImpl();
    }

    @Provides
    SettingsPresenter  provideSettingsPresenter(){
        return new SettingsPresenterImpl();
    }

    @Provides
    UserResetPasswordPresenter provideUserResetPasswordPresenter(){
        return new UserResetPasswordPresenterImpl();
    }

    @Provides
    UserSignUpPresenter provideUserSignUpPresenter(){
        return new UserSignUpPresenterImpl();
    }

    @Provides
    UserLoginPresenter provideUserLoginPresenter(){
        return new UserLoginPresenterImpl();
    }
}

