package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public abstract class FutureExamDao {

    public List<FutureExam> getAll() {
        // Cancella gli esami vecchi, lascia quelli di oggi
        clearOld(Calendar.getInstance().getTimeInMillis() - 60 * 60 * 24 * 1000);
        return get();
    }

    @Query("DELETE FROM FutureExam WHERE Date < :timestamp")
    protected abstract void clearOld(Long timestamp);

    @Query("SELECT * FROM FutureExam ORDER BY Date ASC")
    protected abstract List<FutureExam> get();

    @Query("SELECT * FROM FutureExam WHERE ID = :ID")
    public abstract FutureExam get(int ID);

    @Insert
    public abstract void insert(FutureExam futureExam);

    @Delete
    public abstract void delete(FutureExam futureExam);

    @Update
    public abstract void update(FutureExam futureExam);
}

