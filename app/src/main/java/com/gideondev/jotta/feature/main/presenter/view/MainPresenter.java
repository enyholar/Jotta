package com.gideondev.jotta.feature.main.presenter.view;

import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.base.presenter.Presenter;
import java.util.List;

/**
 * Created by ${ENNY} on 10/19/2017.
 */

public interface MainPresenter extends Presenter<MainView> {

  void OpenEditNote();

  void SetToUpdateNote(NoteModel model);

  void AddNewFolder(String TableName);

  void logUserOut();

  void login();

  void backUpToSdcard();

  void restoreToDb();

  void rateThisApp();

  void ShowFolderData();

  void RefreshFolderList();

  void Start();

  void CreateDefaultSettingsScreen();

  void GetSettingsScreen();

  void OpenSettingsScreen();

  String GetDefaultNoteCount();

  String GetRecycleNoteCount();

  void CompleteDeleteOfNote(NoteModel model);

  void CleanUpTrash();

  void ShowdataPerFolder();

  void sortNoteByDate();

  void ShowdataDefaultNote();

  void ShowTrashNote();

  List<NoteModel> getNoteModelist();

  List<FolderModel> getFolderModelist();

  void refreshAfterRestore();
}
