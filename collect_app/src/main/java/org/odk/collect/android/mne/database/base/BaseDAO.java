package org.odk.collect.android.mne.database.base;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;


public interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... items);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);
}
