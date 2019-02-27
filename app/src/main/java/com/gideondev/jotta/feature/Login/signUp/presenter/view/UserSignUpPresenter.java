package com.gideondev.jotta.feature.Login.signUp.presenter.view;


import com.gideondev.jotta.base.presenter.Presenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by Admin on 5/3/2017.
 */

public interface UserSignUpPresenter
    extends Presenter<UserSignUpView> {
    void SignUp(String email, String password, String retypePassword, FirebaseAuth mAuth);
}
