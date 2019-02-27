package com.gideondev.jotta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 * Created by ENNY on 1/30/2018.
 */
@DatabaseTable(tableName = "tbl_attach")
public class Attach implements Serializable {
  @DatabaseField(generatedId = true)
  protected long id;
  @DatabaseField
  public String attachPath;
  @DatabaseField
  public long noteId;

  public Attach() {
  }

  public Attach(String attachPath, long noteId) {
    this.attachPath = attachPath;
    this.noteId = noteId;
  }

  public Attach(String attachPath) {
    this.attachPath = attachPath;
  }

  @Override
  public String toString() {
    return "Attach{" +
        "id=" + id +
        ", attachPath='" + attachPath + '\'' +
        ", noteId=" + noteId +
        '}';
  }
}
