package com.gideondev.jotta.feature.Login.signUp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gideondev.jotta.R;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.feature.Login.LoginActivity;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserSignUpPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserSignUpView;
import com.gideondev.jotta.internal.ProgressBarHandler;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;

public class UserSignUpActivity
    extends BaseActionbarActivity implements UserSignUpView {

  @Inject
  UserSignUpPresenter mPresenter;
  EditText inputEmail, inputPassword;
  Button btnSignIn, btnSignUp, btnResetPassword;
    ProgressBarHandler progressBar;
  FirebaseAuth mAuth;
  FirebaseAuth.AuthStateListener mAuthListener;
  private DatabaseReference mDatabaseUsers;
  private EditText mRetypePassword;
  private ImageView btnBack;

  @Override
  public void initView() {
    btnSignIn = (Button) findViewById(R.id.sign_in_button);
    btnBack= (ImageView) findViewById(R.id.back_skip);
    btnSignUp = (Button) findViewById(R.id.sign_up_button);
    inputEmail = (EditText) findViewById(R.id.email);
    mRetypePassword = (EditText) findViewById(R.id.retype_password);
    inputPassword = (EditText) findViewById(R.id.password);
    progressBar = new ProgressBarHandler(getContext());
    btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
    findViewById(R.id.sign_up_button).setOnClickListener(this);
    findViewById(R.id.back_skip).setOnClickListener(this);
//    TextView myTextView = (TextView) findViewById(R.id.txt_term);
//    ClickableSpan termsOfServicesClick = new ClickableSpan() {
//      @Override
//      public void onClick(View view) {
//        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.biosec.com.ng"));
//        startActivity(intent);
//      }
//    };


//    makeLinks(myTextView, new String[] { "Terms of services" }, new ClickableSpan[] {
//        termsOfServicesClick
//    });
  }

  public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
    SpannableString spannableString = new SpannableString(textView.getText());
    for (int i = 0; i < links.length; i++) {
      ClickableSpan clickableSpan = clickableSpans[i];
      String link = links[i];

      int startIndexOfLink = textView.getText().toString().indexOf(link);
      spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(spannableString, TextView.BufferType.SPANNABLE);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sign_up_button:
        String password = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();
        String retypePassword = mRetypePassword.getText().toString();
        mPresenter.SignUp(email, password, retypePassword, mAuth);
        break;

      case R.id.back_skip:
        Intent intent= new Intent(UserSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        break;
    }
    super.onClick(v);
  }

  @Override
  public void initModel() {

  }

  @Override
  public Presenter getPresenter() {
    return mPresenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_sign_up);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window w = getWindow(); // in Activity's onCreate() for instance
      w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    initView();
    injectInjector();
  }

  @Override
  protected void injectInjector() {
    DaggerProjectComponent.builder().projectModule(new ProjectModule(this)).build().inject(this);
    mPresenter.setView(this);
    mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void showLineLoading() {
    progressBar.show();
  }

  @Override
  public void hideLineLoading() {
    progressBar.hide();

  }

  @Override
  public void showError(String message) {

  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public Activity getActivity() {
    return this;
  }

  @Override
  public void onResume() {
    super.onResume();
  //  progressBar.hide();
  }
}
