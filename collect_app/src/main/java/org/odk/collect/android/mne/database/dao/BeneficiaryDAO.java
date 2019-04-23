package org.odk.collect.android.mne.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.mne.beneficary.BeneficaryResponse;
import org.odk.collect.android.mne.beneficary.BeneficaryStats;
import org.odk.collect.android.mne.database.base.BaseDAO;

import java.util.List;

@Dao
public abstract class BeneficiaryDAO implements BaseDAO<BeneficaryResponse> {
    @Query("SELECT * from beneficiaries ORDER by name ASC")
    public abstract LiveData<List<BeneficaryResponse>> getById();

    @Query("SELECT *," +
            "(" +
            "SELECT COUNT(id) FROM practical_action_forms " +
            "WHERE beneficiaries.id = beneficiaryId " +
            "AND activityId=:activityId" +
            ")" +
            "AS " +
            "count " +
            "from beneficiaries " +
            "WHERE cluster=:clusterId " +
            "order by name ASC"
    )

    public abstract LiveData<List<BeneficaryStats>> getById(String clusterId, String activityId);


    @Query("DELETE FROM beneficiaries WHERE cluster=:clusterId")
    public abstract void deleteByClusterId(Integer clusterId);
}


