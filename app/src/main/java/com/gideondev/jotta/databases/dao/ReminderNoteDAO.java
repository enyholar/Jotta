package com.gideondev.jotta.databases.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.databases.dto.ReminderNoteDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReminderNoteDAO
    implements ReminderNoteDTO {

  private Connection connection;
  private Context mContext;

  public ReminderNoteDAO(Context context, Connection connection) {
    this.connection = connection;
    this.mContext = context;
  }

  @Override
  public boolean addReminder(Reminder model) {
    try {

      return connection.getReminderWrapDao().create(model) == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean updateReminder(Reminder model) {
    try {
      return connection.getReminderWrapDao().update(model) == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void deleteReminder(String id) {
    try {
      connection.getNoteDataDao().deleteById(Integer.parseInt(id));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Reminder> getAllReminderModels() {
    try {
      return connection.getReminderWrapDao().queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }


  public Reminder getReminderByID(String id) {
    try {
      return connection.getReminderWrapDao().queryForId(Integer.valueOf(id));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Reminder getRemindersByNoteId(@NonNull String noteId) {
    try {
      List<Reminder> list = connection.getReminderWrapDao().queryForEq("noteId", noteId);
      if (list != null && !list.isEmpty()) {
        return list.get(0);
      }
    } catch (SQLException | OutOfMemoryError e) {
      e.printStackTrace();
    }
    return null;
  }


}
