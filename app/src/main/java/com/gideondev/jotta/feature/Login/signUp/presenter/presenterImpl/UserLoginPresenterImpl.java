package com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.gideondev.jotta.R;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserLoginPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserLoginView;
import com.gideondev.jotta.feature.Login.signUp.view.UserResetPasswordActivity;
import com.gideondev.jotta.feature.Login.signUp.view.UserSignUpActivity;
import com.gideondev.jotta.feature.main.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ${ENNY} on 5/18/2017.
 */

public class UserLoginPresenterImpl implements UserLoginPresenter {

  private UserLoginView mView;

  @Override
  public void Login(String email, final String password, FirebaseAuth mAuth) {
    if (TextUtils.isEmpty(email)) {
      Toast.makeText(mView.getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (TextUtils.isEmpty(password)) {
      Toast.makeText(mView.getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
      return;
    }
    mView.showLineLoading();
    //authenticate user
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(mView.getActivity(), new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            mView.hideLineLoading();
            if (!task.isSuccessful()) {
              // there was an error
              if (password.length() < 6) {
                mView.showError(mView.getContext().getString(R.string.minimum_password));
                //inputPassword.setError(getString(R.string.minimum_password));
              } else {
                Toast.makeText(mView.getContext(),
                    mView.getContext().getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
              }
            } else {

              Intent intent = new Intent(mView.getContext(), MainActivity.class);
              mView.getContext().startActivity(intent);
              mView.getActivity().finish();
            }
          }
        }).addOnFailureListener(mView.getActivity(), new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        mView.hideLineLoading();
        Toast.makeText(mView.getContext(),
            mView.getContext().getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public void OpenResetUserScreen() {
    mView.getActivity()
        .startActivity(new Intent(mView.getContext(), UserResetPasswordActivity.class));
  }

  @Override
  public void OpenUserSignUpScreen() {
    mView.getActivity().startActivity(new Intent(mView.getContext(), UserSignUpActivity.class));
  }

  @Override
  public void SkipRegistration() {
    mView.getActivity().startActivity(new Intent(mView.getContext(), MainActivity.class));
  }


  @Override
  public void getExtra(Bundle extra) {

  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setView(@NonNull UserLoginView view) {
    this.mView = view;
  }
}
