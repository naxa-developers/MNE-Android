package org.odk.collect.android.mne.beneficary;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.mne.database.PracticalActionDatabase;
import org.odk.collect.android.mne.database.base.BaseLocalDataSourceRX;
import org.odk.collect.android.mne.database.dao.BeneficiaryDAO;

import java.util.List;

import io.reactivex.Completable;

public class BeneficaryLocalSource implements BaseLocalDataSourceRX<BeneficaryResponse> {
    private static BeneficaryLocalSource INSTANCE = null;
    private BeneficiaryDAO dao;

    public static BeneficaryLocalSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeneficaryLocalSource();
        }
        return INSTANCE;
    }

    private BeneficaryLocalSource() {
        Context context = Collect.getInstance();
        PracticalActionDatabase database = PracticalActionDatabase.getDatabase(context);//todo inject context
        this.dao = database.getBeneficiaryDAO();
    }


    @Override
    public Completable saveCompletable(BeneficaryResponse... items) {
        return Completable.fromAction(() -> dao.insert(items));

    }

    @Override
    public Completable saveCompletable(List<BeneficaryResponse> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    public void save(List<BeneficaryResponse> items) {
        dao.insert(items);
    }

    public LiveData<List<BeneficaryStats>> getById(String clusterID, String activityId) {
        return dao.getById(clusterID, activityId);
    }

    public void deleteByClusterId(Integer clusterId) {
         dao.deleteByClusterId(clusterId);
    }
}
