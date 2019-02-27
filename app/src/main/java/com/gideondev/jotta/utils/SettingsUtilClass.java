package com.gideondev.jotta.utils;

import android.graphics.Typeface;
import android.widget.EditText;
import com.gideondev.jotta.model.SettingsModel;

/**
 * Created by ${ENNY} on 10/30/2017.
 */

public class SettingsUtilClass {

    public static void ChangeTextForm(SettingsModel model, EditText mText){
        if (model.getTextStyle().equals(SettingsModel.mTextStyleBold)){
            mText.setTypeface(Typeface.DEFAULT_BOLD);
        }

        if (model.getTextStyle().equals(SettingsModel.mTextStyleItalic)){
            mText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }

        if (model.getTextStyle().equals(SettingsModel.mTextStyleNormal)){
            mText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

    }

    public static void ChangeTextSize(SettingsModel model, EditText mText){
        mText.setTextSize(model.getTextSize());
    }
}
