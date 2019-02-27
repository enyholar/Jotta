package com.gideondev.jotta.databases.dao;

import android.content.Context;

import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.databases.dto.AttachDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachDAO
    implements AttachDTO {

    private Connection connection;
    private Context mContext;

    public AttachDAO(Context context, Connection connection) {
        this.connection = connection;
        this.mContext = context;
    }


    @Override
    public boolean addAttach(Attach model) {
        try {

            return connection.getAttachWrapDao().create(model) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAttach(Attach model) {
        try {
            return connection.getAttachWrapDao().update(model)==1;
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteAttach(String id) {
        try {
            connection.getAttachWrapDao().deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Attach> getAllAttach() {
        try {
            return connection.getAttachWrapDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



    @Override
    public List<Attach> getAllAttachByNoteId(String noteID) {
        try {
            return connection.getAttachWrapDao().queryForEq("noteId", noteID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }




}
