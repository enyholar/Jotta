package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.NoteCheckListWrap;
import java.util.List;

public interface NoteCheckDTO {

    boolean addCheckNote(NoteCheckListWrap model);

    boolean updateCheckNote(NoteCheckListWrap model);

    void deleteCheckNote(String id);


    List<NoteCheckListWrap> getNoteCheckModels();

    List<NoteCheckListWrap> getNoteCheckByNoteId(String tablename);

    // void onChange(long accountid);
}
