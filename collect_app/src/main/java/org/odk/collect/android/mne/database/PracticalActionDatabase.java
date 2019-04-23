package org.odk.collect.android.mne.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.odk.collect.android.mne.activitygroup.model.Activity;
import org.odk.collect.android.mne.activitygroup.model.ActivityGroup;
import org.odk.collect.android.mne.beneficary.BeneficaryResponse;
import org.odk.collect.android.mne.cluster.Cluster;
import org.odk.collect.android.mne.cluster.ClusterDAO;
import org.odk.collect.android.mne.database.dao.ActivityDAO;
import org.odk.collect.android.mne.database.dao.ActivityGroupDAO;
import org.odk.collect.android.mne.database.dao.BeneficiaryDAO;
import org.odk.collect.android.mne.database.dao.PracticalActionFormsDAO;
import org.odk.collect.android.mne.forms.PraticalActionForm;


@Database(entities =
        {
                BeneficaryResponse.class,
                Activity.class,
                ActivityGroup.class,
                PraticalActionForm.class,
                Cluster.class
        }, version = 1)

@TypeConverters({ActivityTypeConverter.class})
public abstract class PracticalActionDatabase extends RoomDatabase {

    private static PracticalActionDatabase INSTANCE;
    private static final String DB_PATH = "practical_action.db";


    public static PracticalActionDatabase getDatabase(final Context context) {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        synchronized (PracticalActionDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PracticalActionDatabase.class, DB_PATH)
                        .allowMainThreadQueries()//only used in PracticalActionFormsDAO getById(String instanceId)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract ActivityDAO getActivityDAO();

    public abstract BeneficiaryDAO getBeneficiaryDAO();

    public abstract ActivityGroupDAO getActivityGroupDAO();

    public abstract PracticalActionFormsDAO getPracticalActionFormsDAO();

    public abstract ClusterDAO getClusterDAO();
}
