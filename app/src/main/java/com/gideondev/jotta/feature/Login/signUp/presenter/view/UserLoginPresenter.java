package com.gideondev.jotta.feature.Login.signUp.presenter.view;

import android.os.Bundle;
import com.gideondev.jotta.base.presenter.Presenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Admin on 5/3/2017.
 */

public interface UserLoginPresenter
    extends Presenter<UserLoginView> {
    void Login(String email, String password, FirebaseAuth mAuth);
    void OpenResetUserScreen();
    void OpenUserSignUpScreen();

    void SkipRegistration();

    void getExtra(Bundle extra);
}
