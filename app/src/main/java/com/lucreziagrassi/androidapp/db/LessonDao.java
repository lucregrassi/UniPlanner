package com.lucreziagrassi.androidapp.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM Lesson ORDER BY Start")
    List<Lesson> getAll();

    @Query("SELECT * FROM Lesson WHERE Day = :Day ORDER BY Start")
    List<Lesson> getLessonOfDay(Integer Day);

    @Insert
    void insert(Lesson lesson);

    @Delete
    void delete(Lesson lesson);

    @Update
    void update(Lesson lesson);
}

