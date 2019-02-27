package com.gideondev.jotta.feature.Login.signUp.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.gideondev.jotta.R;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserResetPasswordPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserResetPasswordView;
import com.gideondev.jotta.internal.ProgressBarHandler;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;

public class UserResetPasswordActivity
    extends BaseActionbarActivity implements UserResetPasswordView {

  @Inject
  UserResetPasswordPresenter mPresenter;
  private FirebaseAuth mAuth;
  private EditText mInputEmail;
  private ProgressBarHandler progressBar;

  @Override
  public void initView() {
    mInputEmail = (EditText) findViewById(R.id.doc_reset_email);
    progressBar = new ProgressBarHandler(getContext());
    implementOnclick();

  }

  private void implementOnclick() {
    findViewById(R.id.btn_back_login).setOnClickListener(this);
    findViewById(R.id.btn_doc_reset_password).setOnClickListener(this);
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
    setContentView(R.layout.activity_user_reset_password);
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
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_back_login:
        finish();
        break;

      case R.id.btn_doc_reset_password:
        String email = mInputEmail.getText().toString().trim();
        mPresenter.ResetPassword(email, mAuth);
        break;

    }
    super.onClick(v);
  }

  @Override
  public Activity getActivity() {
    return this;
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
}
