package com.gideondev.jotta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by ${ENNY} on 10/19/2017.
 */
@DatabaseTable(tableName = "tbl_note_trash")
public class TrashNoteModel
    implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String tableName;
    @DatabaseField
    private String noteWord;
    @DatabaseField
    private String time;
    @DatabaseField
    private String lastEdited;
    @DatabaseField
    private String title;
    @DatabaseField
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNoteWord() {
        return noteWord;
    }

    public void setNoteWord(String noteWord) {
        this.noteWord = noteWord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }

    public static Comparator<TrashNoteModel> noteNameComparator = new Comparator<TrashNoteModel>() {

        public int compare(TrashNoteModel s1, TrashNoteModel s2) {
            String noteTitle1;
            String noteTitle2 ;
            String note1;
            String note2;

            noteTitle1  = s1.getTitle().toUpperCase();
            noteTitle2 = s2.getTitle().toUpperCase();
            note1  = s1.getNoteWord().toUpperCase();
            note2 = s2.getNoteWord().toUpperCase();
          //  if (!noteTitle1.isEmpty() && !noteTitle2.isEmpty() && noteTitle1 != null && noteTitle2 != null){
                return noteTitle1.compareTo(noteTitle2);
           // }else {
           //     return note1.compareTo(note2);
          //  }

            //ascending order


            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};


    public TrashNoteModel() {

    }
}
