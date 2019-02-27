package com.gideondev.jotta.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by ${ENNY} on 11/1/2017.
 */

public class NoteCheckListWrap {
    @DatabaseField(generatedId = true)
    protected long id;
    @DatabaseField
    private String checkItem;
    @DatabaseField
    private String itemWord;
    @DatabaseField
    private long noteId;

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getItemWord() {
        return itemWord;
    }

    public void setItemWord(String itemWord) {
        this.itemWord = itemWord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
