package com.gideondev.jotta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 * Created by ${ENNY} on 10/19/2017.
 */
@DatabaseTable(tableName = "tbl_settings")
public class SettingsModel
    implements Serializable {
    public static final String mTextStyleNormal = "normal", mTextStyleBold = "bold",mTextStyleItalic = "italic" ;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String textStyle = mTextStyleNormal ;
    @DatabaseField
    private int textSize;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public SettingsModel() {

    }
}
