package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PassedExamDao {
    @Query("SELECT * FROM PassedExam")
    List<PassedExam> getAll();

    @Insert
    void insert(PassedExam passedExam);

    @Delete
    void delete(PassedExam passedExam);
}