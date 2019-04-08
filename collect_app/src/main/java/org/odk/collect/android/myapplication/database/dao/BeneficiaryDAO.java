package org.odk.collect.android.myapplication.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.odk.collect.android.myapplication.beneficary.BeneficaryResponse;
import org.odk.collect.android.myapplication.database.base.BaseDAO;

import java.util.List;

@Dao
public abstract class BeneficiaryDAO implements BaseDAO<BeneficaryResponse> {
    @Query("SELECT * from beneficiaries ORDER by name ASC")
    public abstract LiveData<List<BeneficaryResponse>> getById();
}
