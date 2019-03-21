package org.odk.collect.android.myapplication.beneficary;

import android.content.Context;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.database.dao.BeneficiaryDAO;
import org.odk.collect.android.myapplication.database.PracticalActionDatabase;
import org.odk.collect.android.myapplication.database.base.BaseLocalDataSourceRX;

import java.util.ArrayList;

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
    public Completable save(BeneficaryResponse... items) {
        return Completable.fromAction(() -> dao.insert(items));

    }

    @Override
    public Completable save(ArrayList<BeneficaryResponse> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

}
