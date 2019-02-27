package com.gideondev.jotta.databases.dao;

import android.content.Context;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.databases.dto.NoteCheckDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteCheckDAO
    implements NoteCheckDTO {

    private Connection connection;
    private Context mContext;

    public NoteCheckDAO(Context context, Connection connection) {
        this.connection = connection;
        this.mContext = context;
    }


    //public  boolean checkUserTweetExist(TweetFriendModel model, long account_id) {
    //    try {
    //        return connection.getFriendDataDao().queryBuilder().where().eq("account_id", account_id).and().eq("id_tweet", model.getId_tweet()).query().size() > 0;
    //    } catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //    return false;
    //}


    @Override
    public boolean addCheckNote(NoteCheckListWrap model) {
        try {

            return connection.getNoteCheckListWrapDao().create(model) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCheckNote(NoteCheckListWrap model) {
        try {
            return connection.getNoteCheckListWrapDao().update(model)==1;
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteCheckNote(String id) {
        try {
            connection.getNoteDataDao().deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NoteCheckListWrap> getNoteCheckModels() {
        try {
            return connection.getNoteCheckListWrapDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public NoteModel getNoteModelByID(String id) {
        try {
            return connection.getNoteDataDao().queryForId(Integer.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<NoteCheckListWrap> getNoteCheckByNoteId(String noteID) {
        try {
            return connection.getNoteCheckListWrapDao().queryForEq("noteId", noteID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
