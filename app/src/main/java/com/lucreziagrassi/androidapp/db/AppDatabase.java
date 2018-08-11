package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PassedExam.class,FutureExam.class, User.class, Subject.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PassedExamDao getPassedExamDao();
    public abstract FutureExamDao getFutureExamDao();
    public abstract UserDao getUserDao();
    public abstract SubjectDao getSubjectDao();
}
