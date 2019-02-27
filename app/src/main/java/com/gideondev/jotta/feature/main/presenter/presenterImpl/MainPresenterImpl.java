package com.gideondev.jotta.feature.main.presenter.presenterImpl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.databases.dao.FolderDAO;
import com.gideondev.jotta.databases.dao.NoteCheckDAO;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.ReminderNoteDAO;
import com.gideondev.jotta.databases.dao.SettingsDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.feature.Login.LoginActivity;
import com.gideondev.jotta.feature.main.presenter.view.MainPresenter;
import com.gideondev.jotta.feature.main.presenter.view.MainView;
import com.gideondev.jotta.feature.noteInput.view.NoteInputActivity;
import com.gideondev.jotta.feature.Settings.view.SettingsActivity;
import com.gideondev.jotta.internal.ProgressBarHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ${ENNY} on 10/19/2017.
 */

public class MainPresenterImpl implements MainPresenter {

  private MainView mView;
  private FolderDAO mFolderDao;
  private List<FolderModel> mFolderModelList;
  private FolderModel mCurrentFolder;
  private List<NoteModel> mNoteModelList;
  private NoteDAO mNoteDao;
  private SettingsDAO mSettingsDao;
  List<SettingsModel> mSettingList;
  private SettingsModel mSettingsModel;
  private ArrayList<NoteModel> mAlphabetItems;
  private NoteCheckDAO mNoteCheckDao;
  private ReminderNoteDAO mReminderDao;
  private StorageReference mStorageRef;
  private UploadTask uploadTask;
  private  FirebaseAuth mAuth;
  private ProgressBarHandler mProgressHandler;


  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void OpenEditNote() {
    // GetSettingsScreen();
    if (mView.getCurrentFolder() != null) {
      Intent mIntent = new Intent(mView.getContext(), NoteInputActivity.class);
      Bundle bundle = new Bundle();
      //Add your data from getFactualResults method to bundle
      bundle.putString("add", "Add");
      mIntent.putExtra("model", mView.getCurrentFolder());
      mIntent.putExtra("setting", mSettingsModel);
      mIntent.putExtras(bundle);
      mView.getActivity().startActivityForResult(mIntent, 2);
    } else {
      Intent mIntent = new Intent(mView.getContext(), NoteInputActivity.class);
      Bundle bundle = new Bundle();
      //Add your data from getFactualResults method to bundle
      bundle.putString("add", "Add");
      mIntent.putExtra("name", "My notes");
      mIntent.putExtra("setting", mSettingsModel);
      mIntent.putExtras(bundle);
      mView.getActivity().startActivityForResult(mIntent, 2);
    }
  }

  @Override
  public void SetToUpdateNote(NoteModel model) {
    Reminder reminder = mReminderDao.getRemindersByNoteId(String.valueOf(model.getId()));
    Intent mIntent = new Intent(mView.getContext(), NoteInputActivity.class);
    Bundle bundle = new Bundle();
    //Add your data from getFactualResults method to bundle
    bundle.putString("update", "Update");
    mIntent.putExtra("model", model);
    if (reminder != null) {
      mIntent.putExtra("reminder", reminder);
    }
    mIntent.putExtra("setting", mSettingsModel);
    mIntent.putExtras(bundle);
    mView.getActivity().startActivityForResult(mIntent, 2);
  }


  @Override
  public void AddNewFolder(String TableName) {
    FolderModel model = new FolderModel();
    model.setTableName(TableName);
    mFolderDao.addFolder(model);
    if (!mFolderModelList.isEmpty()) {
      mFolderModelList.clear();
    }
    mFolderModelList.addAll(mFolderDao.getFolderModels());
    mView.NotifyFolderDataSetChange();

  }

  @Override
  public void logUserOut() {
    if (mAuth.getCurrentUser() != null) {
      mAuth.signOut();
      Intent loginIntent = new Intent(mView.getContext(), LoginActivity.class);
      loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      mView.getContext().startActivity(loginIntent);
      mView.getActivity().finish();
    }
  }

  @Override
  public void login() {
      Intent loginIntent = new Intent(mView.getContext(), LoginActivity.class);
      loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      mView.getContext().startActivity(loginIntent);
      mView.getActivity().finish();
  }

  @Override
  public void backUpToSdcard() {
    final String inFileName = mView.getContext().getDatabasePath("easyNote.DB").getPath();
//    final String inFileName = "/data/data/<your.app.package>/databases/foo.db";
    File dbFile = new File(inFileName);
//    FileInputStream fis = null;
//    try {
//      fis = new FileInputStream(dbFile);
//      String outFileName = Environment.getExternalStorageDirectory()+"/database_copy.db";
//
//      // Open the empty db as the output stream
//      OutputStream output = new FileOutputStream(outFileName);
//
//      // Transfer bytes from the inputfile to the outputfile
//      byte[] buffer = new byte[1024];
//      int length;
//      while ((length = fis.read(buffer))>0){
//        output.write(buffer, 0, length);
//      }
//
//      // Close the streams
//      output.flush();
//      output.close();
//      fis.close();
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }


    if(mAuth.getCurrentUser() != null){
      mView.closeDrawer();
      mProgressHandler.show();
      mStorageRef = FirebaseStorage.getInstance().getReference().child("database");
      StorageReference ref = mStorageRef.child(mAuth.getCurrentUser().getUid());
      InputStream stream = null;
      try {
        stream = new FileInputStream(dbFile);
        uploadTask = ref.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
          }
        }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            mProgressHandler.hide();
            Toast.makeText(mView.getContext(), "Note backup successfully", Toast.LENGTH_SHORT).show();
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
          }
        }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Toast.makeText(mView.getContext(), "Failed to backup", Toast.LENGTH_SHORT).show();
            mProgressHandler.hide();
          }
        });
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }else {
      Intent mIntent = new Intent(mView.getContext(), LoginActivity.class);
      mView.getActivity().startActivity(mIntent);
    }
    // Create a storage reference from our app





  }

  @Override
  public void restoreToDb() {
    final String inFileName = mView.getContext().getDatabasePath("easyNote.DB").getPath();
//    final String inFileName = "/data/data/<your.app.package>/databases/foo.db";
//    try {
//      File sd = Environment.getExternalStorageDirectory();
//      File data = Environment.getDataDirectory();
//
//      if (sd.canWrite()) {
//        String currentDBPath = "//data/package name/databases/database_name";
//        String backupDBPath = "database_copy.db";
//        String outFileName = Environment.getExternalStorageDirectory()+"/database_copy.db";
//        File currentDB = new File(inFileName);
//        File backupDB = new File(outFileName);
//
//        if (currentDB.exists()) {
//          FileChannel src = new FileInputStream(backupDB).getChannel();
//          FileChannel dst = new FileOutputStream(currentDB).getChannel();
//          dst.transferFrom(src, 0, src.size());
//          src.close();
//          dst.close();
//          Toast.makeText(mView.getContext(), "Database Restored successfully", Toast.LENGTH_SHORT).show();
//        }
//      }
//    } catch (Exception e) {
//    }

if(mAuth.getCurrentUser() != null){
  mView.closeDrawer();
  mProgressHandler.show();
    mStorageRef = FirebaseStorage.getInstance().getReference().child("database");
    StorageReference ref = mStorageRef.child(mAuth.getCurrentUser().getUid());
    try {
      final File localFile = File.createTempFile("datacopy", "db");
      ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//              Toast.makeText(mView.getContext(),  "file created", Toast.LENGTH_SHORT).show();
          File currentDB = new File(inFileName);
          try {
            FileChannel src = new FileInputStream(localFile).getChannel();
            FileChannel dst = new FileOutputStream(currentDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            mProgressHandler.hide();
            refreshAfterRestore();
            Toast.makeText(mView.getContext(), "Note Restored successfully", Toast.LENGTH_SHORT).show();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }

          //TODO: download audio
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          mProgressHandler.hide();
          Toast.makeText(mView.getContext(),  "An error accoured", Toast.LENGTH_SHORT).show();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

  }else {
    Intent mIntent = new Intent(mView.getContext(), LoginActivity.class);
    mView.getActivity().startActivity(mIntent);
  }

  }

  @Override
  public void rateThisApp() {
    final String appPackageName =
        mView.getContext().getPackageName(); // getPackageName() from Context or Activity object
    try {
      mView.getContext()
          .startActivity(new Intent(Intent.ACTION_VIEW,
              Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      mView.getContext()
          .startActivity(new Intent(Intent.ACTION_VIEW,
              Uri.parse("https://play.google.com/store/apps/details?id="
                  + appPackageName)));
    }
  }


  @Override
  public void ShowFolderData() {
    mFolderModelList = new ArrayList<>();
    mFolderModelList.addAll(mFolderDao.getFolderModels());
    mView.SetUpFolderAdapter(mFolderModelList);

  }

  @Override
  public void RefreshFolderList() {
    if (!getFolderModelist().isEmpty()) {
      getFolderModelist().clear();
    }
    getFolderModelist().addAll(mFolderDao.getFolderModels());
    mView.SetUpFolderAdapter(getFolderModelist());
    // mView.NotifyFolderDataSetChange();

  }

  @Override
  public void Start() {
    mNoteModelList = new ArrayList<>();
    mSettingList = new ArrayList<>();
    mFolderDao = new FolderDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mNoteDao = new NoteDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mSettingsDao = new SettingsDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mReminderDao = new ReminderNoteDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mAuth = FirebaseAuth.getInstance();
    mProgressHandler = new ProgressBarHandler(mView.getActivity());

  }


  @Override
  public void CreateDefaultSettingsScreen() {
    mSettingList.addAll(mSettingsDao.getSettingsModels());
    if (mSettingList.isEmpty()) {
      SettingsModel model = new SettingsModel();
      model.setTextSize(14);
      model.setTextStyle(SettingsModel.mTextStyleNormal);
      mSettingsDao.addSettings(model);
    }
  }

  @Override
  public void GetSettingsScreen() {
    mSettingList.addAll(mSettingsDao.getSettingsModels());
    for (SettingsModel child : mSettingList) {
      mSettingsModel = child;
    }

  }

  @Override
  public void OpenSettingsScreen() {
    mSettingList.addAll(mSettingsDao.getSettingsModels());
    for (SettingsModel child : mSettingList) {
      mSettingsModel = child;
    }
    Intent intent = new Intent(mView.getContext(), SettingsActivity.class);
    intent.putExtra("model", mSettingsModel);
    mView.getActivity().startActivityForResult(intent, 3);
  }


  public void initialiseData(List<NoteModel> mNoteList) {
    //Recycler view data

    //Alphabet fast scroller data
    mAlphabetItems = new ArrayList<>();
    List<NoteModel> strAlphabets = new ArrayList<>();
    for (int i = 0; i < mNoteModelList.size(); i++) {
      String name;
      if (mNoteList.get(i).getTitle() != null && !mNoteList.get(i).getTitle().isEmpty()) {
        name = String.valueOf(mNoteList.get(i).getTitle());
      } else {
        name = String.valueOf(mNoteList.get(i).getNoteWord());
      }
      if (name == null || name.trim().isEmpty()) {
        continue;
      }

      String word = name.substring(0, 1);
      if (!strAlphabets.contains(word)) {
        strAlphabets.add(mNoteList.get(i));
        mAlphabetItems.add(mNoteList.get(i));
      }
    }
  }

  @Override
  public String GetDefaultNoteCount() {
    List<NoteModel> notelist = new ArrayList<>();
    notelist = mNoteDao.getNoteModelsByTableName("My notes");
    int count = notelist.size();
    return String.valueOf(count);
  }

  @Override
  public String GetRecycleNoteCount() {
    List<NoteModel> notelist = new ArrayList<>();
    notelist = mNoteDao.getNoteModelsByTableName("Trash");
    int count = notelist.size();
    return String.valueOf(count);
  }

  @Override
  public void CompleteDeleteOfNote(NoteModel model) {
    mNoteCheckDao = new NoteCheckDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    List<NoteCheckListWrap> mNoteCheckListWrap = new ArrayList<>();
    mNoteCheckListWrap = mNoteCheckDao.getNoteCheckByNoteId(String.valueOf(model.getId()));
    mNoteDao.deleteNote(String.valueOf(model.getId()));
    if (mNoteCheckListWrap != null && !mNoteCheckListWrap.isEmpty()) {
      for (int i = 0; i < mNoteCheckListWrap.size(); i++) {
        mNoteCheckDao.deleteCheckNote(String.valueOf(mNoteCheckListWrap.get(i).getId()));
      }
    }

  }

  @Override
  public void CleanUpTrash() {
    mNoteDao = new NoteDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mNoteCheckDao = new NoteCheckDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    if (mNoteModelList != null && !mNoteModelList.isEmpty()) {
      for (int i = 0; i < mNoteModelList.size(); i++) {
        List<NoteCheckListWrap> mNoteCheckListWrap = new ArrayList<>();
        mNoteCheckListWrap = mNoteCheckDao
            .getNoteCheckByNoteId(String.valueOf(mNoteModelList.get(i).getId()));
        mNoteDao.deleteNote(String.valueOf(mNoteModelList.get(i).getId()));
        for (int is = 0; is < mNoteCheckListWrap.size(); is++) {
          if (mNoteCheckListWrap != null && !mNoteCheckListWrap.isEmpty()) {
            mNoteCheckDao.deleteCheckNote(String.valueOf(mNoteCheckListWrap.get(i).getId()));
          }
        }
      }
    }


  }

  @Override
  public void ShowdataPerFolder() {
    if (!mNoteModelList.isEmpty()) {
      mNoteModelList.clear();
    }

    if (mView.getCurrentFolder() != null && mView.getCurrentFolder().getTableName() != null) {
      mNoteModelList
          .addAll(mNoteDao.getNoteModelsByTableName(mView.getCurrentFolder().getTableName()));
    } else {
      mNoteModelList.addAll(mNoteDao.getNoteModelsByTableName("My notes"));
    }
    sortNoteByDate();
//    Collections.sort(mNoteModelList, NoteModel.noteNameComparator);

    // Collections.sort(mNoteModelList, comp);
    mView.SetUpNoteAdapter(mNoteModelList);
    initialiseData(mNoteModelList);
  }

  @Override
  public void sortNoteByDate() {
    Collections.sort(mNoteModelList, new Comparator<NoteModel>() {

      public int compare(NoteModel lhs, NoteModel rhs) {

        try {
          @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatlhs = new SimpleDateFormat(
              "MMMM d, yyyy hh:mm:a");
          Date convertedDatelhs = dateFormatlhs.parse(lhs.getTime());
          Calendar calendarlhs = Calendar.getInstance();
          calendarlhs.setTime(convertedDatelhs);

          @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatrhs = new SimpleDateFormat(
              "MMMM d, yyyy hh:mm:a");
          Date convertedDaterhs = dateFormatrhs.parse(rhs.getTime());
          Calendar calendarrhs = Calendar.getInstance();
          calendarrhs.setTime(convertedDaterhs);

          if (calendarlhs.getTimeInMillis() > calendarrhs.getTimeInMillis()) {

            return -1;
          } else {

            return 1;

          }
        } catch (ParseException e) {

          e.printStackTrace();
        }

        return 0;
      }
    });
  }

  @Override
  public void ShowdataDefaultNote() {
    if (!mNoteModelList.isEmpty()) {
      mNoteModelList.clear();
    }

    mNoteModelList.addAll(mNoteDao.getNoteModelsByTableName("My notes"));
    sortNoteByDate();
    mView.SetUpNoteAdapter(mNoteModelList);
    initialiseData(mNoteModelList);
  }

  @Override
  public void ShowTrashNote() {
    if (!mNoteModelList.isEmpty()) {
      mNoteModelList.clear();
    }

    mNoteModelList.addAll(mNoteDao.getNoteModelsByTableName("Trash"));
    sortNoteByDate();
    mView.SetUpNoteAdapter(mNoteModelList);
    initialiseData(mNoteModelList);
  }

  @Override
  public List<NoteModel> getNoteModelist() {
    return mNoteModelList;
  }

  @Override
  public List<FolderModel> getFolderModelist() {
    return mFolderModelList;
  }

  @Override
  public void refreshAfterRestore() {
//    mView.closeDrawer();
//    ShowdataPerFolder();

    mView.getActivity().finish();
    mView.getActivity().overridePendingTransition(0, 0);
    mView.getActivity().startActivity( mView.getActivity().getIntent());
    mView. getActivity().overridePendingTransition(0, 0);
  }

  @Override
  public void destroy() {

  }

  @Override
  public void setView(@NonNull MainView view) {
    this.mView = view;
  }
}
