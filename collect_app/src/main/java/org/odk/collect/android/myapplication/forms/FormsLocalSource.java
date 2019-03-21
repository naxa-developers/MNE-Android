package org.odk.collect.android.myapplication.forms;

import android.content.Context;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.database.PracticalActionDatabase;
import org.odk.collect.android.myapplication.database.base.BaseLocalDataSourceRX;
import org.odk.collect.android.myapplication.database.dao.PracticalActionFormsDAO;

import java.util.List;
import io.reactivex.Completable;
public class FormsLocalSource implements BaseLocalDataSourceRX<PraticalActionForm> {

    private static FormsLocalSource INSTANCE = null;
    private PracticalActionFormsDAO dao;

    public static FormsLocalSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FormsLocalSource();
        }
        return INSTANCE;
    }

    private FormsLocalSource() {
        Context context = Collect.getInstance();
        PracticalActionDatabase database = PracticalActionDatabase.getDatabase(context);//todo inject context
        this.dao = database.getPracticalActionFormsDAO();
    }


    @Override
    public Completable saveCompletable(PraticalActionForm... items) {
        return Completable.fromAction(() -> dao.insert(items));
    }

    @Override
    public Completable saveCompletable(List<PraticalActionForm> items) {
        return Completable.fromAction(() -> dao.insert(items));
    }


    public void save(PraticalActionForm... items) {
        dao.insert(items);
    }

}
