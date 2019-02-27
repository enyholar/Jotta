package com.gideondev.jotta.feature.Settings.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.ChangeThemeUtils;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.feature.Settings.presenter.view.SettingsPresenter;
import com.gideondev.jotta.feature.Settings.presenter.view.SettingsView;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import javax.inject.Inject;

public class SettingsActivity
    extends BaseActionbarActivity implements SettingsView {

    @Inject
    SettingsPresenter mPresenter;
    private Toolbar toolbar;
    private TextView mTextStyle;
    private TextView mTextSize;

    @Override
    public void initView() {
       findViewById(R.id.ln_rate).setOnClickListener(this);
       findViewById(R.id.ln_text_style).setOnClickListener(this);
       findViewById(R.id.ln_text_size).setOnClickListener(this);
        mTextStyle = (TextView) findViewById(R.id.text_style);
        mTextSize = (TextView) findViewById(R.id.text_size);

    }

    @Override
    public void initModel() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ln_text_style:
                displayPopupWindowTheme(findViewById(R.id.ln_text_style));
                break;

            case R.id.ln_rate:

                break;

            case R.id.ln_text_size:
                CreateTextSize();
                break;
        }
        super.onClick(v);
    }

    @Override
    public void SetUpView(SettingsModel model){
        mTextStyle.setText(model.getTextStyle());
        mTextSize.setText(String.valueOf(model.getTextSize()));
    }


    private void CreateTextSize() {
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View dialogLayout = inflater.inflate(R.layout.custom_dialog_new_text_edit_text, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        TextView title = new TextView(SettingsActivity.this);
        final EditText mNewFolderEditText =
            (EditText) dialogLayout.findViewById(R.id.new_folder_edit_text);
        title.setText(getApplicationContext().getResources().getString(R.string.txt_size));
        mNewFolderEditText.setText(String.valueOf(mPresenter.getmSettingsModel().getTextSize()));
        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                          LinearLayout.LayoutParams.WRAP_CONTENT);
        title.setPadding(50, 50, 0, 0);
        title.setLayoutParams(lp);
        title.setTextSize(20);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextColor(getApplicationContext().getResources()
                               .getColor(R.color.dialog_title_black));
        builder.setCustomTitle(title);
        builder.setView(dialogLayout);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(),
                               mNewFolderEditText.getText().toString(),
                               Toast.LENGTH_LONG).show();
                String size = mNewFolderEditText.getText().toString();
                mPresenter.getmSettingsModel().setTextSize(Integer.valueOf(size));
                mPresenter.UpdateSettings(mPresenter.getmSettingsModel());
                mTextSize.setText(size);
            }
        });

        AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();
        Button btnPositive = customAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = customAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setTextColor(getApplicationContext().getResources()
                                     .getColor(R.color.dialog_button_black));
        btnPositive.setTextColor(getApplicationContext().getResources()
                                     .getColor(R.color.dialog_button_black));
        btnNegative.setTextSize(14);
        btnPositive.setTypeface(btnPositive.getTypeface(), Typeface.BOLD);
        btnNegative.setTypeface(btnPositive.getTypeface(), Typeface.BOLD);

        LinearLayout.LayoutParams layoutParams =
            (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        //layoutParams.weight = 5;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }

    private void iniActionbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChangeThemeUtils.onActivityCreateSetTheme(getActivity(),getContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        iniActionbar();
        initView();
        injectInjector();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent();
                setResult(3,intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(3,intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
            .projectModule(new ProjectModule(this))
            .build()
            .inject(this);
        mPresenter.setView(this);
        mPresenter.Start();
        mPresenter.getExtra(getIntent().getExtras());
    }

    private void displayPopupWindowTheme(View anchorView) {
        PopupMenu popup = new PopupMenu(getActivity(), anchorView);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setGravity(Gravity.START);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.select_italic:
                        if (mPresenter.getmSettingsModel() != null) {
                            mPresenter.UpdateStyle(SettingsModel.mTextStyleItalic);
                            mTextStyle.setText(getContext().getResources().getString(R.string.italic));
                        }
                        break;
                    case R.id.select_bold:
                        if (mPresenter.getmSettingsModel() != null) {
                            mPresenter.UpdateStyle(SettingsModel.mTextStyleBold);
                            mTextStyle.setText(getContext().getResources().getString(R.string.bold));
                        }
                        break;

                    case R.id.select_normal:
                        if (mPresenter.getmSettingsModel() != null) {
                            mPresenter.UpdateStyle(SettingsModel.mTextStyleNormal);
                            mTextStyle.setText(getContext().getResources().getString(R.string.normal));
                        }
                        break;
                }
                return false;
            }
        });
        inflater.inflate(R.menu.text_style_menu, popup.getMenu());
        popup.show();

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLineLoading() {

    }

    @Override
    public void hideLineLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
