package com.gideondev.jotta.feature.main.adapter;

import com.gideondev.jotta.model.NoteModel;

/**
 * Created by ${ENNY} on 10/20/2017.
 */

public interface NoteListner {
    void OnItemClick(NoteModel model, int p);
    void OnLongClick(NoteModel model, int p);
}
