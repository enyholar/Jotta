package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.Reminder;
import java.util.List;

public interface ReminderNoteDTO {

    boolean addReminder(Reminder model);

    boolean updateReminder(Reminder model);

    void deleteReminder(String id);


    List<Reminder> getAllReminderModels();

   // List<TrashNoteModel> getNoteModelsByTableName(String tablename);

    // void onChange(long accountid);
}
