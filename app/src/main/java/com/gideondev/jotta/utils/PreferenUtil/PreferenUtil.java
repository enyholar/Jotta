package com.gideondev.jotta.utils.PreferenUtil;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tinh Vu on 27/09/2016.
 */

public class PreferenUtil {


    public static final String COLOR_KEY = "color";
    public static final String SMARTLIST_KEY = "SMARTLIST";
    public static final String SKIP_REGISTRATION_KEY = "SKIPREGISTRATIONKEY";


    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static PreferenUtil mInstant = null;

    private PreferenUtil(Context context) {
        mContext = context;
    }

    public static PreferenUtil getInstant(Context context) {
        if (mInstant == null) {
            mInstant = new PreferenUtil(context);
           // PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(mListener);
        }
        return mInstant;
    }

    public  void SaveTheme(String color){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(COLOR_KEY, color);
        sharedPreferencesEditor.apply();

    }

    public String getThemeColor(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(COLOR_KEY,"");
    }

    public boolean checkSkipRegistration() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getBoolean(SKIP_REGISTRATION_KEY, false);
    }

  public void enableSkipRegistration(boolean isChecked) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
    sharedPreferencesEditor.putBoolean(SKIP_REGISTRATION_KEY, isChecked);
    sharedPreferencesEditor.apply();
  }
}
