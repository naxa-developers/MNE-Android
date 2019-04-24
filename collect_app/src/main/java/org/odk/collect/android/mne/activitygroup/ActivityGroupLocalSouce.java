package org.odk.collect.android.mne.activitygroup;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.mne.activitygroup.model.ActivityGroup;
import org.odk.collect.android.mne.database.PracticalActionDatabase;
import org.odk.collect.android.mne.database.base.BaseLocalDataSourceRX;
import org.odk.collect.android.mne.database.dao.ActivityGroupAndActivity;
import org.odk.collect.android.mne.database.dao.ActivityGroupDAO;

import java.util.List;

import io.reactivex.Completable;

public class ActivityGroupLocalSouce implements BaseLocalDataSourceRX<ActivityGroup> {
    private static ActivityGroupLocalSouce INSTANCE = null;
    private final ActivityGroupDAO dao;


    public static ActivityGroupLocalSouce getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityGroupLocalSouce();
        }
        return INSTANCE;
    }

    private ActivityGroupLocalSouce() {
        Context context = Collect.getInstance();
        PracticalActionDatabase database = PracticalActionDatabase.getDatabase(context);//todo inject context
        this.dao = database.getActivityGroupDAO();
    }


    @Override
    public Completable saveCompletable(ActivityGroup... items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    @Override
    public Completable saveCompletable(List<ActivityGroup> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    public LiveData<List<ActivityGroup>> getById(String clusterId) {
        return dao.getById(clusterId);
    }

    public LiveData<List<ActivityGroupAndActivity>> getActGroupAndActById(String clusterId) {
        return dao.getActGrpAndActById();
    }

    public void save(List<ActivityGroup> items) {
        dao.insert(items);
    }


    public void deleteAll() {
        dao.deleteAll();
    }
}
