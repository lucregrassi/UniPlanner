package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exam")
    List<Exam> getAll();

    @Insert
    void insert(Exam exam);

    @Delete
    void delete(Exam exam);
}
