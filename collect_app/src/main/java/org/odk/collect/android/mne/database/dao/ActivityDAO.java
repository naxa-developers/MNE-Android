package org.odk.collect.android.mne.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.mne.activitygroup.model.Activity;
import org.odk.collect.android.mne.database.base.BaseDAO;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ActivityDAO implements BaseDAO<Activity> {
    @Query("SELECT * from activities WHERE activity_group_id=:activityGroupId")
    public abstract LiveData<List<Activity>> getById(String activityGroupId);

    @Query("SELECT * from activities WHERE activity_group_id=:activityGroupId")
    public abstract Single<List<Activity>> getByIdAsSingle(String activityGroupId);


    @Query("select *,(" +
            "select count(activityId)" +
            "from practical_action_forms " +
            "WHERE activities.id = practical_action_forms.activityId " +
            "GROUP BY activityId " +
            ")" +
            "filled_forms_count " +
            "from activities " +
            "WHERE  activity_group_id=:activityGroupId")
    public abstract LiveData<List<Activity>> getByGroupId(String activityGroupId);
}
