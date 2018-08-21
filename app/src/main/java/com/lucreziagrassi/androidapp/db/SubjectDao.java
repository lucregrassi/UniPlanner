package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject ORDER BY Subject ASC")
    List<Subject> getAll();

    @Query("SELECT * FROM Subject WHERE ID = :id")
    Subject get(int id);

    @Query("SELECT * FROM Subject WHERE Subject = :subject")
    Subject get(String subject);

    @Insert
    void insert(Subject subject);

    @Delete
    void delete(Subject subject);
}
