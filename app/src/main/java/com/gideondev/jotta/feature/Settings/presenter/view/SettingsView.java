package com.gideondev.jotta.feature.Settings.presenter.view;

import android.app.Activity;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.base.presenter.LoadDataView;

/**
 * Created by ${ENNY} on 10/25/2017.
 */

public interface SettingsView extends LoadDataView {
    void SetUpView(SettingsModel model);

    Activity getActivity();
}
