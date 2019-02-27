package com.gideondev.jotta.feature.noteInput.presenter.view;

import android.app.Activity;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.base.presenter.LoadDataView;
import com.gideondev.jotta.databases.sqlite.DatabaseHelper;
import java.util.List;

/**
 * Created by ${ENNY} on 10/20/2017.
 */

public interface NoteInputView extends LoadDataView {

  void SetUpReminderView(Reminder model);

  void SetUpView(NoteModel model, List<NoteCheckListWrap> mNoteCheckList);

  void SetUpTextFormat(SettingsModel model);

  void setUpDefaultAlarmValue(Reminder reminder);

  Activity getActivity();

  List<Attach> getNoteAttachFilePath();

  DatabaseHelper getHelper();
}
