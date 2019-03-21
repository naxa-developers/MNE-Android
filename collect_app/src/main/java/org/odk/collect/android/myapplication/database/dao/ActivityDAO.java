package org.odk.collect.android.myapplication.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.database.base.BaseDAO;

import java.util.List;

@Dao
public abstract class ActivityDAO implements BaseDAO<Activity> {
    @Query("SELECT * from activities")
    public abstract LiveData<List<Activity>> getById();
}
