package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FutureExamDao {
    @Query("SELECT * FROM FutureExam")
    List<FutureExam> getAll();

    @Insert
    void insert(FutureExam futureExam);

    @Delete
    void delete(FutureExam futureExam);
}

