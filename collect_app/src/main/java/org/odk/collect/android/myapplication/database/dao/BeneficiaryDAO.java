package org.odk.collect.android.myapplication.database.dao;

import android.arch.persistence.room.Dao;

import org.odk.collect.android.myapplication.beneficary.BeneficaryResponse;
import org.odk.collect.android.myapplication.database.base.BaseDAO;

@Dao
public abstract class BeneficiaryDAO implements BaseDAO<BeneficaryResponse> {
}
