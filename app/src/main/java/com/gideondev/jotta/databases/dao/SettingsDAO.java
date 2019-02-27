package com.gideondev.jotta.databases.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.databases.dto.SettingsDTO;
import com.gideondev.jotta.databases.sqlite.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingsDAO
    implements SettingsDTO {

    private Connection connection;
    private Context mContext;

    public SettingsDAO(Context context, Connection connection) {
        this.connection = connection;
        this.mContext = context;
    }


    @Override
    public boolean addSettings(SettingsModel model) {
        try {

            return connection.getSettingsDao().create(model) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSettings(SettingsModel model) {
        try {
            return connection.getSettingsDao().update(model)==1;
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteSettings(String id) {
        try {
            connection.getNoteDataDao().deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SettingsModel> getSettingsModels() {
        try {
            return connection.getSettingsDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public SettingsModel getSettingsModelByID(String id) {
        try {
            return connection.getSettingsDao().queryForId(Integer.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  NoteModel getNoteModelById(@NonNull String id) {
        try {
            List<NoteModel> list = connection.getNoteDataDao().queryForEq("username", id);
            if (list != null && !list.isEmpty())
                return list.get(0);
        } catch (SQLException | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }



}
