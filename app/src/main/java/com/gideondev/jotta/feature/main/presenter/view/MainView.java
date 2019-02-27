package com.gideondev.jotta.feature.main.presenter.view;

import android.app.Activity;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.base.presenter.LoadDataView;
import java.util.List;

/**
 * Created by ${ENNY} on 10/19/2017.
 */

public interface MainView extends LoadDataView {
    void SetUpFolderAdapter(List<FolderModel> modelList);

    void SetUpNoteAdapter(List<NoteModel> modelList);

  void closeDrawer();

  void NotifyFolderDataSetChange();

    FolderModel getCurrentFolder();

    Activity getActivity();
}
