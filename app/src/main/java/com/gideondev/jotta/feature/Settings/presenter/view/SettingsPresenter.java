package com.gideondev.jotta.feature.Settings.presenter.view;

import android.os.Bundle;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.base.presenter.Presenter;

/**
 * Created by ${ENNY} on 10/25/2017.
 */

public interface SettingsPresenter extends Presenter<SettingsView> {

    void getExtra(Bundle extra);

    SettingsModel getmSettingsModel();

    void Start();

    void UpdateSettings(SettingsModel model);

    void UpdateStyle(String mTextStyleBold);

}
