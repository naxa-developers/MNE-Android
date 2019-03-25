package org.odk.collect.android.myapplication.activity;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.database.PracticalActionDatabase;
import org.odk.collect.android.myapplication.database.base.BaseLocalDataSourceRX;
import org.odk.collect.android.myapplication.database.dao.ActivityDAO;

import java.util.List;

import io.reactivex.Completable;

public class ActivityLocalSource implements BaseLocalDataSourceRX<Activity> {
    private static ActivityLocalSource INSTANCE = null;
    private ActivityDAO dao;

    public static ActivityLocalSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityLocalSource();
        }
        return INSTANCE;
    }

    private ActivityLocalSource() {
        Context context = Collect.getInstance();
        PracticalActionDatabase database = PracticalActionDatabase.getDatabase(context);//todo inject context
        this.dao = database.getActivityDAO();
    }


    @Override
    public Completable saveCompletable(Activity... items) {
        return Completable.fromAction(() -> dao.insert(items));

    }

    @Override
    public Completable saveCompletable(List<Activity> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    public void save(List<Activity> items) {
        dao.insert(items);
    }

    public void save(Activity...items) {
        dao.insert(items);
    }


    public LiveData<List<Activity>> getById(String activityGroupId) {
        return dao.getById(activityGroupId);
    }
}