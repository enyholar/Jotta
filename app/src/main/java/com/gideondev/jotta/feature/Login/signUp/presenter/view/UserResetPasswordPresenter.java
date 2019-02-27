package com.gideondev.jotta.feature.Login.signUp.presenter.view;


import com.gideondev.jotta.base.presenter.Presenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Admin on 5/3/2017.
 */

public interface UserResetPasswordPresenter
    extends Presenter<UserResetPasswordView> {
    void ResetPassword(String email, FirebaseAuth mAuth);
}
