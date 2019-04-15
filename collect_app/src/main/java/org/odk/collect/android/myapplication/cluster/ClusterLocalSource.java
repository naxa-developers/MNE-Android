package org.odk.collect.android.myapplication.cluster;

import android.arch.lifecycle.LiveData;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.database.PracticalActionDatabase;
import org.odk.collect.android.myapplication.database.base.BaseLocalDataSourceRX;

import java.util.List;

import io.reactivex.Completable;

public class ClusterLocalSource implements BaseLocalDataSourceRX<Cluster> {
    private static ClusterLocalSource INSTANCE = null;
    private ClusterDAO dao;

    public static ClusterLocalSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClusterLocalSource();
        }
        return INSTANCE;
    }

    private ClusterLocalSource() {
        this.dao = PracticalActionDatabase.getDatabase(Collect.getInstance()).getClusterDAO();
    }


    @Override
    public Completable saveCompletable(Cluster... items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    @Override
    public Completable saveCompletable(List<Cluster> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    public void save(List<Cluster> items) {
        dao.insert(items);
    }

    public LiveData<List<Cluster>> getAll() {
        return dao.getAll();
    }


    void deleteAll() {
        dao.deleteAll();
    }
}
