package com.gideondev.jotta.databases.dto;

import com.gideondev.jotta.model.NoteModel;
import java.util.List;

public interface NoteDTO {

    boolean addNote(NoteModel model);

    boolean updateNote(NoteModel model);

    void deleteNote(String id);


    List<NoteModel> getNoteModels();

    List<NoteModel> getNoteModelsByTableName(String tablename);

    // void onChange(long accountid);
}
