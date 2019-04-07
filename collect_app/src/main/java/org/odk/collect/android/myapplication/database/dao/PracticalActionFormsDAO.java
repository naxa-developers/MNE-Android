package org.odk.collect.android.myapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.myapplication.database.base.BaseDAO;
import org.odk.collect.android.myapplication.forms.PraticalActionForm;

import io.reactivex.Single;

@Dao
public abstract class PracticalActionFormsDAO implements BaseDAO<PraticalActionForm> {

    @Query("SELECT * from practical_action_forms WHERE instanceId=:instanceId")
    public abstract PraticalActionForm getById(String instanceId);

    @Query("SELECT COUNT(*) from practical_action_forms WHERE activityId=:activityId")
    public abstract Single<Integer> getCountByActivityIdAsSingle(String activityId);
}
