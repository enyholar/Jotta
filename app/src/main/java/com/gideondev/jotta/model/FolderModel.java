package com.gideondev.jotta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 * Created by ${ENNY} on 10/20/2017.
 */
@DatabaseTable(tableName = "tbl_folder")
public class FolderModel implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String tableName;

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

    public FolderModel() {
    }

    @Override
    public String toString() {
        return "FolderModel{" + "id='" + id + '\'' + ", tableName='" + tableName + '\'' + '}';
    }
}
