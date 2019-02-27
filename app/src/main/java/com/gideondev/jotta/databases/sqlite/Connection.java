package com.gideondev.jotta.databases.sqlite;

import android.content.Context;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.j256.ormlite.dao.Dao;

public class Connection extends DatabaseHelper {

  private static Connection instance;
  // the DAO object we use to access the UserModel table
  private Dao<NoteModel, Integer> noteDao = null;
  private Dao<FolderModel, Integer> folderDao = null;
  private Dao<SettingsModel, Integer> settingsDao = null;
  private Dao<NoteCheckListWrap, Integer> noteCheckListWrapsDao = null;
  private Dao<Reminder, Integer> reminderDao = null;
  private Dao<Attach, Integer> attachDao = null;

  public Connection(Context context) {
    super(context);
  }

  public static Connection getInstance(Context context) {
    if (instance == null) {
      instance = new Connection(context);
    }
    return instance;
  }


  /**
   * Returns the Database Access Object (DAO) for our TweetFriendModel class. It will
   * create it or just give the cached value.
   */
  public Dao<NoteModel, Integer> getNoteDataDao()
      throws java.sql.SQLException {
    if (noteDao == null) {
      noteDao = getDao(NoteModel.class);
    }
    return noteDao;
  }

  public Dao<FolderModel, Integer> getFolderDataDao()
      throws java.sql.SQLException {
    if (folderDao == null) {
      folderDao = getDao(FolderModel.class);
    }
    return folderDao;
  }

  public Dao<SettingsModel, Integer> getSettingsDao()
      throws java.sql.SQLException {
    if (settingsDao == null) {
      settingsDao = getDao(SettingsModel.class);
    }
    return settingsDao;
  }

  public Dao<NoteCheckListWrap, Integer> getNoteCheckListWrapDao()
      throws java.sql.SQLException {
    if (noteCheckListWrapsDao == null) {
      noteCheckListWrapsDao = getDao(NoteCheckListWrap.class);
    }
    return noteCheckListWrapsDao;
  }

  public Dao<Reminder, Integer> getReminderWrapDao()
      throws java.sql.SQLException {
    if (reminderDao == null) {
      reminderDao = getDao(Reminder.class);
    }
    return reminderDao;
  }

  public Dao<Attach, Integer> getAttachWrapDao()
      throws java.sql.SQLException {
    if (attachDao == null) {
      attachDao = getDao(Attach.class);
    }
    return attachDao;
  }

  /**
   * Close the database connections and clear any cached DAOs.
   */
  @Override
  public void close() {
    super.close();
    noteDao = null;
    folderDao = null;
    settingsDao = null;
    noteCheckListWrapsDao = null;
    reminderDao = null;
 //   attachDao = null;
  }
}
