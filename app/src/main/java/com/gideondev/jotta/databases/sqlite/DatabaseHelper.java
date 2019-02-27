package com.gideondev.jotta.databases.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper
    extends OrmLiteSqliteOpenHelper {


  public DatabaseHelper(Context context) {
    super(context, DatabaseConfig.DATABASE_NAME, null, DatabaseConfig.DATABASE_VERSION);
    Log.d("", "start Db");
  }

  /**
   * This is called when the database is first created. Usually you should
   * call createTable statements here to create the tables that will store
   * your data.
   */
  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      Log.i("create", "onCreate");
      TableUtils.createTableIfNotExists(connectionSource, NoteModel.class);
      TableUtils.createTableIfNotExists(connectionSource, FolderModel.class);
      TableUtils.createTableIfNotExists(connectionSource, SettingsModel.class);
      TableUtils.createTableIfNotExists(connectionSource, NoteCheckListWrap.class);
      TableUtils.createTableIfNotExists(connectionSource, Reminder.class);
    } catch (SQLException e) {
      Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * This is called when your application is upgraded and it has a higher
   * version number. This allows you to adjust the various data to match the
   * new version number.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db,
      ConnectionSource connectionSource,
      int oldVersion,
      int newVersion) {
    try {
      if (oldVersion < 12){
        TableUtils.createTableIfNotExists(connectionSource, Attach.class);
        Log.i("upgrade", "onUpgrade");
      }

    } catch (SQLException e) {
      Log.e("error", "Can't drop databases", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Close the database connections and clear any cached DAOs.
   */
  @Override
  public void close() {
    super.close();
  }


}
