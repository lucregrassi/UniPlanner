package com.lucreziagrassi.androidapp.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM Lesson")
    List<Lesson> getAll();

    @Insert
    void insert(Lesson lesson);

    @Delete
    void delete(Lesson lesson);
}

