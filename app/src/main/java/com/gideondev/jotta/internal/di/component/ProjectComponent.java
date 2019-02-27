package com.gideondev.jotta.internal.di.component;

import com.gideondev.jotta.feature.Login.LoginActivity;
import com.gideondev.jotta.feature.Login.signUp.view.UserResetPasswordActivity;
import com.gideondev.jotta.feature.Login.signUp.view.UserSignUpActivity;
import com.gideondev.jotta.feature.main.view.MainActivity;
import com.gideondev.jotta.feature.noteInput.view.NoteInputActivity;
import com.gideondev.jotta.feature.Settings.view.SettingsActivity;
import com.gideondev.jotta.internal.di.PerActivity;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import dagger.Component;

/**
 * Created by Enny on 29/11/2016.
 */
@PerActivity
@Component(modules = ProjectModule.class)
public interface ProjectComponent {
    void inject(MainActivity mainActivity);
    void inject(NoteInputActivity noteInputActivity);
    void inject(SettingsActivity settingsActivity);
    void inject(LoginActivity loginActivity);
    void inject(UserResetPasswordActivity userResetPasswordActivity);
    void inject(UserSignUpActivity userSignUpActivity);
    //void inject(RealTimeECGMeasurementActivity realTimeECGMeasurementActivity);
    //void inject(EcgPatientListActivity ecgPatientListActivity);
}
