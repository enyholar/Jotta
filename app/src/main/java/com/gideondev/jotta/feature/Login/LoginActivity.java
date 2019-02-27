package com.gideondev.jotta.feature.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.PreferenUtil.PreferenUtil;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.databinding.ActivityLoginBinding;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserLoginPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserLoginView;
import com.gideondev.jotta.feature.main.view.MainActivity;
import com.gideondev.jotta.internal.ProgressBarHandler;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import javax.inject.Inject;

public class LoginActivity extends BaseActionbarActivity implements UserLoginView {
//  private LoginButton fbLogin;
  private Button emailLogin;
  private CallbackManager callbackManager;
  LinearLayout btnSkip;
  private FirebaseAuth auth;
  ProgressBarHandler progressBar;
  @Inject
  UserLoginPresenter mPresenter;
  private ActivityLoginBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window w = getWindow(); // in Activity's onCreate() for instance
      w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    progressBar = new ProgressBarHandler(getContext());
    iniActionbar();
    initView();
    injectInjector();
    initModel();
  }

  @Override
  protected void injectInjector() {
    DaggerProjectComponent.builder()
        .projectModule(new ProjectModule(this))
        .build()
        .inject(this);
    mPresenter.setView(this);
  }

  private void iniActionbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(R.string.login);
    toolbar.setTitleTextColor(Color.WHITE);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  public void initModel() {
    auth = FirebaseAuth.getInstance();
    callbackManager = CallbackManager.Factory.create();
    setFbLogin();
  }

  @Override
  public Presenter getPresenter() {
    return mPresenter;
  }
  @Override
  public void initView() {
  //  fbLogin = findViewById(R.id.btn_facebook);
    btnSkip = findViewById(R.id.skip);
    Button openEmailLogin = findViewById(R.id.ln_signUp_email);
    openEmailLogin.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mPresenter.OpenUserSignUpScreen();
      }
    });
    binding.btnFacebook.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays
            .asList("public_profile", "user_friends"));

      }
    });

    binding.userLogin.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mPresenter.Login(binding.edtUsername.getText().toString(),binding.edtPassword.getText().toString(),auth);
      }
    });
    binding.frgetPassword.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mPresenter.OpenResetUserScreen();
      }
    });
   binding.backSkip.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        PreferenUtil preferenUtil = PreferenUtil.getInstant(getContext());
        preferenUtil.enableSkipRegistration(true);
        openMainActivity();
      }
    });
  }


  private void setFbLogin(){
    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        Log.d(
            "success", "facebook:onSuccess:" + loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
      }

      @Override
      public void onCancel() {
        Log.d("Cancel", "facebook:onCancel");

      }

      @Override
      public void onError(FacebookException error) {
        Log.d("Error", "facebook:onError", error);
        // [START_EXCLUDE]

      }
    });
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Pass the activity result back to the Facebook SDK
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }
  // [END on_activity_result]
  private void handleFacebookAccessToken(AccessToken token) {
    Log.d("Token", "handleFacebookAccessToken:" + token);

    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    auth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d("tasksuccess", "signInWithCredential:success");
              FirebaseUser user = auth.getCurrentUser();
              openMainActivity();
            } else {
              // If sign in fails, display a message to the user.
              Log.w("fail", "signInWithCredential:failure", task.getException());
              Toast.makeText(LoginActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
              //updateUI(null);
            }

          }
        });
  }

  private void openMainActivity(){
    Intent intent  = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void onBackPressed() {
    PreferenUtil preferenUtil = PreferenUtil.getInstant(getContext());
    preferenUtil.enableSkipRegistration(true);
    openMainActivity();
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
