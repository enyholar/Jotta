package com.gideondev.jotta.databases.dao;

import android.content.Context;
import com.gideondev.jotta.databases.sqlite.Connection;

public abstract class DAOFactory {

    // List of storage types supported by the factory
    public static final int SQLITE_DATABASE = 1;
    public static final int SHARE_PREFERENCE = 2;
    public static final int INTERNAL_STORAGE = 3;
    public static final int EXTERNAL_STORAGE = 4;
    public static final int NETWORK_CONNECTION = 5;
    protected Connection mConnection;
    protected Context mContext;

    public DAOFactory(Context context) {
        mContext = context;
    }

    /**
     * @param context
     * @param whichFactory
     * @return
     */
    public static DAOFactory getDAOFactory(Context context, int whichFactory) {
        switch (whichFactory) {
            case SQLITE_DATABASE:
                return new SqliteDAOFactory(context);
            default:
                return null;
        }
    }


    public abstract Connection getConnection();

    public abstract void closeConnection();

}
