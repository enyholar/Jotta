package com.gideondev.jotta.feature.Settings.presenter.presenterImpl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.databases.dao.SettingsDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.feature.Settings.presenter.view.SettingsPresenter;
import com.gideondev.jotta.feature.Settings.presenter.view.SettingsView;

/**
 * Created by ${ENNY} on 10/25/2017.
 */

public class SettingsPresenterImpl implements SettingsPresenter {
    private SettingsView mView;
    private SettingsModel mSettingsModel;
    private SettingsDAO mSettingsDao;

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
    public void setView(@NonNull SettingsView view) {
        this.mView = view;
    }

    @Override
    public void getExtra(Bundle extra) {
        if (extra != null){
           mSettingsModel = (SettingsModel) extra.getSerializable("model");
            mView.SetUpView(mSettingsModel);
        }
    }

    @Override
    public SettingsModel getmSettingsModel(){

        return mSettingsModel;
    }

    @Override
    public void Start() {
        mSettingsDao = new SettingsDAO(mView.getContext(), new SqliteDAOFactory(mView.getContext()).getConnection());
    }

    @Override
    public void UpdateSettings(SettingsModel model){
        mSettingsDao.updateSettings(model);
    }

    @Override
    public void UpdateStyle(String mTextStyleBold) {
        getmSettingsModel().setTextStyle(mTextStyleBold);
        mSettingsDao.updateSettings(getmSettingsModel());
    }
}
