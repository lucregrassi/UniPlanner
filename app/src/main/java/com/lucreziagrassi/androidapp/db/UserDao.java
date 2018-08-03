package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    public abstract User getUser();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract long insert(User user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(User user);

    public void setUser(User user) {
        // Essendoci un unico User nel DB, provo ad inserire una nuova riga
        // oppure aggiorno quella esistente
        long id = insert(user);

        if (id == -1) {
            update(user);
        }
    }
}
