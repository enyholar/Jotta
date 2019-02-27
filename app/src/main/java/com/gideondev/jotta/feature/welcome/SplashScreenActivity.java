package com.gideondev.jotta.feature.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.PreferenUtil.PreferenUtil;
import com.gideondev.jotta.feature.Login.LoginActivity;
import com.gideondev.jotta.feature.main.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
  private static int SPLASH_TIME_OUT = 3000;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
 //   NightOwl.builder().defaultMode(0).create();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    setStatusBarTranslucent(true);
    start();
    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        checkUserLoginOrNot();
      }
    }, SPLASH_TIME_OUT);
  }

  private void start(){
    mAuth = FirebaseAuth.getInstance();
  }
  private void checkUserLoginOrNot(){
    PreferenUtil preferenUtil = PreferenUtil.getInstant(getApplicationContext());
    if (mAuth.getCurrentUser() != null | preferenUtil.checkSkipRegistration()){
      Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    }else{
      Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }

  protected void setStatusBarTranslucent(boolean makeTranslucent) {
    if (makeTranslucent) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    } else {
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }
}
