package com.gideondev.jotta.feature.noteInput.presenter.presenterImpl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.databases.dao.AttachDAO;
import com.gideondev.jotta.databases.dao.NoteCheckDAO;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.ReminderNoteDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.feature.Alarm.AlarmReceiver;
import com.gideondev.jotta.feature.noteInput.presenter.view.NoteInputPresenter;
import com.gideondev.jotta.feature.noteInput.presenter.view.NoteInputView;
import com.gideondev.jotta.feature.noteInput.view.NoteInputActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ${ENNY} on 10/20/2017.
 */

public class NoteInputPresenterImpl implements NoteInputPresenter {

  private NoteInputView mView;
  private FolderModel mFolderModel;
  private NoteDAO mNoteDao;
  private NoteCheckDAO mNoteCheckDao;
  private NoteModel mNoteModel;
  private SettingsModel mSettingsModel;
  private List<NoteCheckListWrap> mNoteCheckListWrap = new ArrayList<>();
  private static final long milMinute = 60000L;
  private static final long milHour = 3600000L;
  private static final long milDay = 86400000L;
  private static final long milWeek = 604800000L;
  private static final long milMonth = 2592000000L;
  private ReminderNoteDAO mReminderDao;
  private Calendar mCalendar;
  private String reminderId;
  private Reminder reminder;
  private List<Attach> noteAttachList;
  private AttachDAO mAttachDao;

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setView(@NonNull NoteInputView view) {
    this.mView = view;
  }

  @Override
  public void getExtra(Intent intent) {
    if (intent.getStringExtra("add") != null) {
      if (intent.getStringExtra("add").equals("Add")) {
        mFolderModel = (FolderModel) intent.getSerializableExtra("model");
        mSettingsModel = (SettingsModel) intent.getSerializableExtra("setting");
        mView.SetUpTextFormat(mSettingsModel);
      }
    } else if (intent.getStringExtra("update") != null) {
      mNoteModel = (NoteModel) intent.getSerializableExtra("model");
      mSettingsModel = (SettingsModel) intent.getSerializableExtra("setting");
      reminder = (Reminder) intent.getSerializableExtra("reminder");
      if (intent.getStringExtra("update").equals("Update")) {
        mNoteCheckListWrap = mNoteCheckDao.getNoteCheckByNoteId(String.valueOf(mNoteModel.getId()));
        noteAttachList = mAttachDao.getAllAttachByNoteId(String.valueOf(mNoteModel.getId()));
        for (Attach attach : noteAttachList) {
          mView.getNoteAttachFilePath().add(attach);
        }

        mView.SetUpView(mNoteModel, mNoteCheckListWrap);
        mView.SetUpTextFormat(mSettingsModel);
        mView.SetUpReminderView(reminder);
        mView.setUpDefaultAlarmValue(reminder);
      }
    } else if (intent.getStringExtra("alarm") != null) {
      reminderId = intent.getStringExtra(NoteInputActivity.EXTRA_REMINDER_ID);
      mSettingsModel = (SettingsModel) intent.getSerializableExtra("setting");
      if (intent.getStringExtra("alarm").equals("Alarm")) {
        reminder = mReminderDao.getReminderByID(reminderId);
        mNoteModel = mNoteDao.getNoteModelByID(reminder.getNoteId());
        mNoteCheckListWrap = mNoteCheckDao.getNoteCheckByNoteId(String.valueOf(mNoteModel.getId()));
        mNoteCheckListWrap = mNoteCheckDao.getNoteCheckByNoteId(String.valueOf(mNoteModel.getId()));
        noteAttachList = mAttachDao.getAllAttachByNoteId(String.valueOf(mNoteModel.getId()));
        for (Attach attach : noteAttachList) {
          mView.getNoteAttachFilePath().add(attach);
        }
        mView.SetUpView(mNoteModel, mNoteCheckListWrap);
        mView.SetUpTextFormat(mSettingsModel);
        mView.SetUpReminderView(reminder);
        mView.setUpDefaultAlarmValue(reminder);
      }
    }


  }

  @Override
  public void ShareNote(String Note, String title, List<NoteCheckListWrap> mItemList) {
    if (!Note.isEmpty()) {
      Intent sharingIntent = new Intent(Intent.ACTION_SEND);
      sharingIntent.setType("text/html");
      if (title.isEmpty() && !Note.isEmpty() && mItemList == null) {
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Note:" + Note);
      } else if (!title.isEmpty() && !Note.isEmpty() && mItemList == null) {
        sharingIntent
            .putExtra(android.content.Intent.EXTRA_TEXT, "Title:" + title + "\n" + "Note:" + Note);
      } else if (!title.isEmpty() && !Note.isEmpty() && mItemList != null) {

        List<String> stringData = new ArrayList<String>();
        for (int i = 0; i < mItemList.size(); i++) {
          stringData.add(mItemList.get(i).getItemWord());
        }
        StringBuilder sb = new StringBuilder();
        if (stringData != null) {

          for (String s : stringData) {
            sb.append(s);
            sb.append("\n");
          }
        }
        String item = sb.toString();
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
            "Title:" + title + "\n" + "Note:" + Note + "\n" + "Item list:" + "\n" +
                item);
      } else if (title.isEmpty() && !Note.isEmpty() && mItemList != null) {
        List<String> stringData = new ArrayList<String>();
        for (int i = 0; i < mItemList.size(); i++) {
          stringData.add(mItemList.get(i).getItemWord());
        }
        StringBuilder sb = new StringBuilder();
        if (stringData != null) {

          for (String s : stringData) {
            sb.append(s);
            sb.append("\n");
          }
        }
        String item = sb.toString();
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
            "Note:" + Note + "\n" + "Item list:" + "\n" +
                item);
      }
      mView.getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
  }

  @Override
  public String getCurrentTimeStamp() {
    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat(
        "MMMM d, yyyy hh:mm:a");//dd/MM/yyyy
    Date now = new Date();
    //Date da = Calendar.getInstance().getTime();
    String strDate = sdfDate.format(now);
    return strDate;
  }

  @Override
  public void SaveNote(String note, String title, List<NoteCheckListWrap> mNoteCheckList,
      String color, Boolean changeColor, int mYear, int mMonth, int mHour, int mMinute,
      int mDay, long mRepeatTime,
      String mTime, String mDate, String mRepeat, String mRepeatNo,
      String mRepeatType,
      String mActive, List<Attach> attachList) {
    if (mNoteModel != null) {
      String time = getCurrentTimeStamp();
      mNoteModel.setTableName(mNoteModel.getTableName());
      mNoteModel.setTitle(title);
      mNoteModel.setNoteWord(note);
      mNoteModel.setLastEdited(getCurrentTimeStamp());
      if (changeColor) {
        mNoteModel.setColor(color);
      }

      mNoteDao.updateNote(mNoteModel);
      if (mNoteModel != null) {
        for (int i = 0; i < mNoteCheckList.size(); i++) {
          //this is when new item is added to existing item in array list for saving
          if (i > mNoteCheckListWrap.size() - 1) {
            NoteCheckListWrap mCheckNoteWrp = new NoteCheckListWrap();
            mCheckNoteWrp.setItemWord(mNoteCheckList.get(i).getItemWord());
            mCheckNoteWrp.setCheckItem(mNoteCheckList.get(i).getCheckItem());
            mCheckNoteWrp.setNoteId(mNoteModel.getId());
            mNoteCheckDao.addCheckNote(mCheckNoteWrp);
          } else {
            mNoteCheckListWrap.get(i).setItemWord(mNoteCheckList.get(i).getItemWord());
            mNoteCheckListWrap.get(i).setCheckItem(mNoteCheckList.get(i).getCheckItem());
            mNoteCheckListWrap.get(i).setNoteId(mNoteModel.getId());
            mNoteCheckDao.updateCheckNote(mNoteCheckListWrap.get(i));
          }

        }
      }

      if (reminder != null) {
        updateReminder(mYear, mMonth, mHour, mMinute, mDay, mRepeatTime, mTime, mDate, mRepeat,
            mRepeatNo, mRepeatType, mActive, reminder);
      }
    } else {
      NoteModel model = new NoteModel();
      String time = getCurrentTimeStamp();
      if (mFolderModel != null) {
        model.setTableName(mFolderModel.getTableName());
      } else {
        model.setTableName("My notes");
      }
      model.setTitle(title);
      model.setNoteWord(note);
      model.setTime(time);
      if (changeColor) {
        model.setColor(color);
      } else {
        model.setColor("white");
      }
      mNoteDao.addNote(model);
      NoteModel noteModel = mNoteDao.getNoteModelById(note);
      if (noteModel != null) {
        for (int i = 0; i < mNoteCheckList.size(); i++) {
          NoteCheckListWrap mCheckNoteWrp = new NoteCheckListWrap();
          mCheckNoteWrp.setItemWord(mNoteCheckList.get(i).getItemWord());
          mCheckNoteWrp.setCheckItem(mNoteCheckList.get(i).getCheckItem());
          mCheckNoteWrp.setNoteId(noteModel.getId());
          mNoteCheckDao.addCheckNote(mCheckNoteWrp);
        }

        if (attachList != null && attachList.size() > 0) {
          for (int i = 0; i < attachList.size(); i++) {
            Attach attach = new Attach(attachList.get(i).attachPath, noteModel.getId());
            mAttachDao.addAttach(attach);
          }
        }

        if (mTime != null && mDate != null && !mTime.isEmpty() & !mDate.isEmpty()) {
          saveReminder(mYear, mMonth, mHour, mMinute, mDay, mRepeatTime, mTime, mDate, mRepeat,
              mRepeatNo,
              mRepeatType, mActive, noteModel);
        }
      }
    }

  }

  public void saveReminder(int mYear, int mMonth, int mHour, int mMinute,
      int mDay, long mRepeatTime,
      String mTime, String mDate, String mRepeat, String mRepeatNo,
      String mRepeatType,
      String mActive, NoteModel model) {

    // Creating Reminder
    mReminderDao.addReminder(
        new Reminder(String.valueOf(model.getId()), mDate, mTime, mRepeat, mRepeatNo, mRepeatType,
            mActive));
    Reminder reminderModel = new Reminder();
    reminderModel = mReminderDao.getRemindersByNoteId(String.valueOf(model.getId()));

    // Set up calender for creating the notification
    mCalendar.set(Calendar.MONTH, --mMonth);
    mCalendar.set(Calendar.YEAR, mYear);
    mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
    mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
    mCalendar.set(Calendar.MINUTE, mMinute);
    mCalendar.set(Calendar.SECOND, 0);

    // Check repeat type
    if (mRepeatType.equals("Minute")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
    } else if (mRepeatType.equals("Hour")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
    } else if (mRepeatType.equals("Day")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
    } else if (mRepeatType.equals("Week")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
    } else if (mRepeatType.equals("Month")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
    }

    // Create a new notification
    if (reminderModel != null) {
      if (mActive.equals("true")) {
        if (mRepeat.equals("true")) {
          new AlarmReceiver()
              .setRepeatAlarm(mView.getContext(), mCalendar, reminderModel.getID(), mRepeatTime);
        } else if (mRepeat.equals("false")) {
          new AlarmReceiver().setAlarm(mView.getContext(), mCalendar, reminderModel.getID());
        }
      }
    }

    // Create toast to confirm new reminder
    Toast.makeText(mView.getContext(), "Saved",
        Toast.LENGTH_SHORT).show();

  }

  public void updateReminder(int mYear, int mMonth, int mHour, int mMinute,
      int mDay, long mRepeatTime,
      String mTime, String mDate, String mRepeat, String mRepeatNo,
      String mRepeatType,
      String mActive, Reminder mReceivedReminder) {
    AlarmReceiver mAlarmReceiver = new AlarmReceiver();
    // Set new values in the reminder
    mReceivedReminder.setDate(mDate);
    mReceivedReminder.setTime(mTime);
    mReceivedReminder.setRepeat(mRepeat);
    mReceivedReminder.setRepeatNo(mRepeatNo);
    mReceivedReminder.setRepeatType(mRepeatType);
    mReceivedReminder.setActive(mActive);
    // Update reminder
    mReminderDao.updateReminder(mReceivedReminder);

    // Set up calender for creating the notification
    mCalendar.set(Calendar.MONTH, --mMonth);
    mCalendar.set(Calendar.YEAR, mYear);
    mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
    mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
    mCalendar.set(Calendar.MINUTE, mMinute);
    mCalendar.set(Calendar.SECOND, 0);

    // Cancel existing notification of the reminder by using its ID
    mAlarmReceiver.cancelAlarm(mView.getContext(), mReceivedReminder.getID());

    // Check repeat type
    if (mRepeatType.equals("Minute")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
    } else if (mRepeatType.equals("Hour")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
    } else if (mRepeatType.equals("Day")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
    } else if (mRepeatType.equals("Week")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
    } else if (mRepeatType.equals("Month")) {
      mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
    }

    // Create a new notification
    if (mActive.equals("true")) {
      if (mRepeat.equals("true")) {
        mAlarmReceiver
            .setRepeatAlarm(mView.getContext(), mCalendar, mReceivedReminder.getID(), mRepeatTime);
      } else if (mRepeat.equals("false")) {
        mAlarmReceiver.setAlarm(mView.getContext(), mCalendar, mReceivedReminder.getID());
      }
    }

    // Create toast to confirm update
    Toast.makeText(mView.getContext(), "Edited",
        Toast.LENGTH_SHORT).show();
  }


  @Override
  public void DeleteNote() {
    if (mNoteModel != null) {
      mNoteModel.setTableName("Trash");
      mNoteDao.updateNote(mNoteModel);

    }
  }


  @Override
  public void Start() {
    mNoteDao = new NoteDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mNoteCheckDao = new NoteCheckDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mReminderDao = new ReminderNoteDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mAttachDao = new AttachDAO(mView.getContext(),
        new SqliteDAOFactory(mView.getContext()).getConnection());
    mCalendar = Calendar.getInstance();
    noteAttachList = new ArrayList<>();
  }


  @Override
  public Reminder getReminder() {
    return reminder;
  }

  @Override
  public String getReminderId() {
    return reminderId;
  }


  @Override
  public List<NoteCheckListWrap> getmNoteCheckListWrap() {
    return mNoteCheckListWrap;
  }


  @Override
  public NoteModel getmNoteModel() {
    return mNoteModel;
  }


}
