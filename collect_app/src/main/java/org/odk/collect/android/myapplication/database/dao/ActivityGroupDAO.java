package org.odk.collect.android.myapplication.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.database.base.BaseDAO;

import java.util.List;

@Dao
public abstract class ActivityGroupDAO implements BaseDAO<ActivityGroup> {
    @Query("SELECT * from activity_groups WHERE cluster=:clusterId")
    public abstract LiveData<List<ActivityGroup>> getById(String clusterId);

    @Query("SELECT * from activity_groups")
    public abstract LiveData<List<ActivityGroupAndActivity>> getActGrpAndActById();

    @Query("DELETE FROM activity_groups")
    public abstract void deleteAll();
}
