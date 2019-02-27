package com.gideondev.jotta.databases.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.databases.dto.FolderDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolderDAO
    implements FolderDTO {

    private Connection connection;
    private Context mContext;

    public FolderDAO(Context context, Connection connection) {
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
    public boolean addFolder(FolderModel model) {
        try {

            return connection.getFolderDataDao().create(model) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateFolder(FolderModel model) {
        try {
            return connection.getFolderDataDao().update(model)==1;
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteFolder(String id) {
        try {
            connection.getNoteDataDao().deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FolderModel> getFolderModels() {
        try {
            return connection.getFolderDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public FolderModel getNoteModelByID(String id) {
        try {
            return connection.getFolderDataDao().queryForId(Integer.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  FolderModel getNoteModelById(@NonNull String id) {
        try {
            List<FolderModel> list = connection.getFolderDataDao().queryForEq("username", id);
            if (list != null && !list.isEmpty())
                return list.get(0);
        } catch (SQLException | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

}
