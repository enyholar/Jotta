package com.gideondev.jotta.feature.noteInput.presenter.view;

import android.content.Intent;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.base.presenter.Presenter;
import java.util.List;

/**
 * Created by ${ENNY} on 10/20/2017.
 */

public interface NoteInputPresenter extends Presenter<NoteInputView> {
    void getExtra(Intent extras);

    void ShareNote(String Note,String title,List<NoteCheckListWrap> mItemList);

    String getCurrentTimeStamp();

    void SaveNote(String note, String title, List<NoteCheckListWrap> mNoteCheckList, String color,Boolean changeColor, int mYear, int mMonth, int mHour, int mMinute,
        int mDay, long mRepeatTime,
        String mTime, String mDate, String mRepeat, String mRepeatNo,
        String mRepeatType,
        String mActive,List<Attach> mAttachList);


    void DeleteNote();

    void Start();

  Reminder getReminder();

  String getReminderId();

  List<NoteCheckListWrap> getmNoteCheckListWrap();

    NoteModel getmNoteModel();
}
