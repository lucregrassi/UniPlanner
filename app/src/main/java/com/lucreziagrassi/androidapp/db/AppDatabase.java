package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Exam.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ExamDao getExamDao();
}
