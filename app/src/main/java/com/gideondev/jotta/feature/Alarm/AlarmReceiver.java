/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.gideondev.jotta.feature.Alarm;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.ReminderNoteDAO;
import com.gideondev.jotta.databases.dao.SettingsDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.feature.noteInput.view.NoteInputActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmReceiver extends WakefulBroadcastReceiver {

  AlarmManager mAlarmManager;
  PendingIntent mPendingIntent;
  SettingsModel mSettingsModel;
  String mTitle;

  @Override
  public void onReceive(Context context, Intent intent) {
    int mReceivedID = Integer.parseInt(intent.getStringExtra(NoteInputActivity.EXTRA_REMINDER_ID));

    // Get notification title from Reminder Database
    ReminderNoteDAO mReminderDao = new ReminderNoteDAO(context,
        new SqliteDAOFactory(context).getConnection());
    NoteDAO mNoteDao = new NoteDAO(context, new SqliteDAOFactory(context).getConnection());
    SettingsDAO mSettingsDao = new SettingsDAO(context, new SqliteDAOFactory(context).getConnection());
    List<SettingsModel> mSettingList = new ArrayList<>();
    mSettingList.addAll(mSettingsDao.getSettingsModels());
    for (SettingsModel child : mSettingList) {
      mSettingsModel = child;
    }
    Reminder reminder = mReminderDao.getReminderByID(String.valueOf(mReceivedID));
    NoteModel noteModel = mNoteDao.getNoteModelByID(String.valueOf(reminder.getNoteId()));
    if (noteModel.getTitle() != null && !noteModel.getTitle().isEmpty()){
      mTitle = noteModel.getTitle();
    }else {
      mTitle = noteModel.getNoteWord();
    }


    // Create intent to open ReminderEditActivity on notification click
    Intent editIntent = new Intent(context, NoteInputActivity.class);
    Bundle bundle = new Bundle();
    //Add your data from getFactualResults method to bundle
    bundle.putString("alarm", "Alarm");
    bundle.putString(NoteInputActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
    //ArrayList<Che>
    bundle.putSerializable("setting", mSettingsModel);
    editIntent.putExtras(bundle);
    editIntent.putExtra(NoteInputActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));

    PendingIntent mClick = PendingIntent
        .getActivity(context, mReceivedID, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    // Create Notification
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
        .setSmallIcon(R.drawable.ic_access_time_black_24dp)
        .setContentTitle(context.getResources().getString(R.string.app_name))
        .setTicker(mTitle)
        .setContentText(mTitle)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        .setContentIntent(mClick)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true);

    NotificationManager nManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    nManager.notify(mReceivedID, mBuilder.build());
  }

  public void setAlarm(Context context, Calendar calendar, int ID) {
    mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    // Put Reminder ID in Intent Extra
    Intent intent = new Intent(context, AlarmReceiver.class);
    intent.putExtra(NoteInputActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
    mPendingIntent = PendingIntent
        .getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    // Calculate notification time
    Calendar c = Calendar.getInstance();
    long currentTime = c.getTimeInMillis();
    long diffTime = calendar.getTimeInMillis() - currentTime;

    // Start alarm using notification time
    mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
        SystemClock.elapsedRealtime() + diffTime,
        mPendingIntent);

    // Restart alarm if device is rebooted
    ComponentName receiver = new ComponentName(context, BootReceiver.class);
    PackageManager pm = context.getPackageManager();
    pm.setComponentEnabledSetting(receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP);
  }

  public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
    mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    // Put Reminder ID in Intent Extra
    Intent intent = new Intent(context, AlarmReceiver.class);
    intent.putExtra(NoteInputActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
    mPendingIntent = PendingIntent
        .getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    // Calculate notification timein
    Calendar c = Calendar.getInstance();
    long currentTime = c.getTimeInMillis();
    long diffTime = calendar.getTimeInMillis() - currentTime;

    // Start alarm using initial notification time and repeat interval time
    mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
        SystemClock.elapsedRealtime() + diffTime,
        RepeatTime, mPendingIntent);

    // Restart alarm if device is rebooted
    ComponentName receiver = new ComponentName(context, BootReceiver.class);
    PackageManager pm = context.getPackageManager();
    pm.setComponentEnabledSetting(receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP);
  }

  public void cancelAlarm(Context context, int ID) {
    mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    // Cancel Alarm using Reminder ID
    mPendingIntent = PendingIntent
        .getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
    mAlarmManager.cancel(mPendingIntent);

    // Disable alarm
    ComponentName receiver = new ComponentName(context, BootReceiver.class);
    PackageManager pm = context.getPackageManager();
    pm.setComponentEnabledSetting(receiver,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP);
  }
}