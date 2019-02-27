package com.gideondev.jotta.feature.Common;

import android.support.v4.app.FragmentManager;
import com.gideondev.jotta.R;
import com.gideondev.jotta.feature.sketch.NoteActionActivity;
import com.gideondev.jotta.feature.sketch.NoteSketchFragment;

/**
 * Created by ENNY on 1/16/2018.
 */

public class NoteActionNavigationNoteController {

  private final int containerId;
  private final FragmentManager fragmentManager;

  public NoteActionNavigationNoteController(NoteActionActivity actionActivity) {
    this.containerId = R.id.container;
    this.fragmentManager = actionActivity.getSupportFragmentManager();
  }

  public void navigateSketch(String filePath) {
    NoteSketchFragment fragment = NoteSketchFragment
        .newInstance(filePath);
    fragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commitAllowingStateLoss();
  }

}
