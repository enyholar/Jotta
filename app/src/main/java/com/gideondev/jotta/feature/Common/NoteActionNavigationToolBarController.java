package com.gideondev.jotta.feature.Common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.v7.widget.Toolbar;
import com.gideondev.jotta.R;
import com.gideondev.jotta.feature.sketch.NoteActionActivity;
import com.gideondev.jotta.feature.sketch.NoteSketchFragment;

/**
 * Created by ENNY on 1/16/2018.
 */

public class NoteActionNavigationToolBarController {

  @Nullable
  private Toolbar toolbar;

  /**
   * Must call after MainActivity onCreate
   */
  public NoteActionNavigationToolBarController(final NoteActionActivity actionActivity) {
    toolbar = actionActivity.findViewById(R.id.toolbar);
    actionActivity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(
        new FragmentLifecycleCallbacks() {

          @Override
          public void onFragmentActivityCreated(FragmentManager fm, Fragment f,
              Bundle savedInstanceState) {
            if (toolbar != null) {
              reset();
            }
            if (f instanceof NoteSketchFragment) {
              updateForNoteSketchFragment(actionActivity);
            }

          }
        }, true);
  }

  private void updateForNoteSketchFragment(NoteActionActivity activity) {
    activity.setTitle(R.string.noteaction_sketch_toolbartitle);
  }

  private void reset() {
    if (toolbar != null) {
      toolbar.getMenu().clear();
    }
  }


}
