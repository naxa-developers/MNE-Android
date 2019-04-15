package org.odk.collect.android.myapplication.cluster;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.myapplication.database.base.BaseDAO;

import java.util.List;

@Dao
public abstract class ClusterDAO implements BaseDAO<Cluster> {

    @Query("SELECT * from clusters")
    public abstract LiveData<List<Cluster>> getAll();

    @Query("DELETE FROM clusters")
    public abstract void deleteAll();
}
