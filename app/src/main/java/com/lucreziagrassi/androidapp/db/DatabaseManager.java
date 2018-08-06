package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {

    private static AppDatabase appDB = null;

    public static void initializeDatabase(Context context)
    {
        // Crea o apre il DB
        appDB = Room.databaseBuilder(context, AppDatabase.class, "uniplanner_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getDatabase()
    {
        return appDB;
    }
}
