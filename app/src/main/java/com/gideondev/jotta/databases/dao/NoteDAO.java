package com.gideondev.jotta.databases.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.databases.dto.NoteDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO
    implements NoteDTO {

    private Connection connection;
    private Context mContext;

    public NoteDAO(Context context, Connection connection) {
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
    public boolean addNote(NoteModel model) {
        try {

            return connection.getNoteDataDao().create(model) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateNote(NoteModel model) {
        try {
            return connection.getNoteDataDao().update(model)==1;
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteNote(String id) {
        try {
            connection.getNoteDataDao().deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NoteModel> getNoteModels() {
        try {
            return connection.getNoteDataDao().queryForAll();
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

    public  NoteModel getNoteModelById(@NonNull String word) {
        try {
            List<NoteModel> list = connection.getNoteDataDao().queryForEq("noteWord", word);
            if (list != null && !list.isEmpty())
                return list.get(0);
        } catch (SQLException | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<NoteModel> getNoteModelsByTableName(String tablename) {
        try {
            return connection.getNoteDataDao().queryForEq("tableName", tablename);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



}
