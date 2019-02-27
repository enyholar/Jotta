package com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.gideondev.jotta.R;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserResetPasswordPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserResetPasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${ENNY} on 5/18/2017.
 */

public class UserResetPasswordPresenterImpl
    implements UserResetPasswordPresenter {

  private UserResetPasswordView mView;

  @Override
  public void ResetPassword(String email, FirebaseAuth mAuth) {
    if (TextUtils.isEmpty(email) & emailValidator(email)) {
      Toast.makeText(mView.getContext(), "Enter your registered email id or Incorrect Email",
          Toast.LENGTH_SHORT).show();
      return;
    }

    if (emailValidator(email)) {
      mView.showLineLoading();
      mAuth.sendPasswordResetEmail(email)
          .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                Toast.makeText(mView.getContext(),
                    "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT)
                    .show();
              } else {
                Toast.makeText(mView.getContext(),
                    mView.getContext().getString(R.string.reset_failed), Toast.LENGTH_LONG).show();
              }

              mView.hideLineLoading();
            }
          }).addOnFailureListener(mView.getActivity(), new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          mView.hideLineLoading();
          Toast.makeText(mView.getContext(),
              mView.getContext().getString(R.string.reset_failed), Toast.LENGTH_LONG).show();
        }
      });
    } else {
      Toast.makeText(mView.getContext(), "Check email format", Toast.LENGTH_SHORT).show();
    }

  }

  private boolean emailValidator(String email) {
    Pattern pattern;
    Matcher matcher;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
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
  public void setView(@NonNull UserResetPasswordView view) {
    this.mView = view;
  }
}
