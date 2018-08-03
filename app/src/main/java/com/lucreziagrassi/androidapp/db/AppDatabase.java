package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PassedExam.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PassedExamDao getExamDao();
}
