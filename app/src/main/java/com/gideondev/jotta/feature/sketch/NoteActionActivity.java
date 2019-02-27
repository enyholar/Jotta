package com.gideondev.jotta.feature.sketch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.gideondev.jotta.R;
import com.gideondev.jotta.databinding.ActivityNoteActionBinding;
import com.gideondev.jotta.feature.Common.NoteActionNavigationNoteController;
import com.gideondev.jotta.feature.Common.NoteActionNavigationToolBarController;

public class NoteActionActivity extends AppCompatActivity {
  public static final String ARGS_FRAGMENT_TYPE = "action";
  public static final String ARGS_FRAGMENT_FILE_PATH = "file_path";
  private int actionsType;
  private NoteActionNavigationToolBarController toolbarController;
  private ActivityNoteActionBinding binding;
  private FragmentManager mFragmentManager;
  public String picturePath;
  private String filePath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    NoteActionNavigationNoteController navigationNoteController = new NoteActionNavigationNoteController(this);
    readArgs(savedInstanceState);
    mFragmentManager = getSupportFragmentManager();
    binding = DataBindingUtil.setContentView(this, R.layout.activity_note_action);
    toolbarController = new NoteActionNavigationToolBarController(this);
    setupActionBar();

    if (savedInstanceState == null){
      switch (actionsType){
        case 1:
          navigationNoteController.navigateSketch(filePath);
          break;
      }
    }
  }

  private void readArgs(Bundle savedInstanceState) {
    Bundle args = getIntent() != null && getIntent().getExtras() != null ? getIntent().getExtras()
        : savedInstanceState;
    if (args != null) {
      actionsType = args.getInt(ARGS_FRAGMENT_TYPE);
      filePath = args.getString(ARGS_FRAGMENT_FILE_PATH);
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    saveArgs(outState);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        super.onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupActionBar() {
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
  }

  private void saveArgs(Bundle outState) {
    outState.putInt(ARGS_FRAGMENT_TYPE, actionsType);
  }

  @Override
  public void onBackPressed() {
    Fragment f;

    // SketchFragment
    f = checkFragmentInstance(R.id.container, NoteSketchFragment.class);
    if (f != null) {
      ((NoteSketchFragment) f).save();

      // Removes forced portrait orientation for this fragment
      setRequestedOrientation(
          ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

      mFragmentManager.popBackStack();
      Intent data = new Intent();
      data.putExtra("picture", picturePath);
      updateAtExternal(Activity.RESULT_OK, data);
      return;
    }
    super.onBackPressed();
  }

  private Fragment checkFragmentInstance(int id, Object instanceClass) {
    Fragment result = null;
    if (mFragmentManager != null) {
      Fragment fragment = mFragmentManager.findFragmentById(id);
      if (instanceClass.equals(fragment.getClass())) {
        result = fragment;
      }
    }
    return result;
  }

  public void updateAtExternal(int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      setResult(resultCode, data);
      finish();
    }
  }
}
