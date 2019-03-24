package org.odk.collect.android.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.beneficary.BeneficaryResponse;
import org.odk.collect.android.myapplication.database.dao.ActivityDAO;
import org.odk.collect.android.myapplication.database.dao.ActivityGroupDAO;
import org.odk.collect.android.myapplication.database.dao.BeneficiaryDAO;
import org.odk.collect.android.myapplication.database.dao.PracticalActionFormsDAO;
import org.odk.collect.android.myapplication.forms.PraticalActionForm;


@Database(entities =
        {
                BeneficaryResponse.class,
                Activity.class,
                ActivityGroup.class,
                PraticalActionForm.class
        }, version = 2)

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
                        .fallbackToDestructiveMigration()//todo: remove this once we are live
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract ActivityDAO getActivityDAO();

    public abstract BeneficiaryDAO getBeneficiaryDAO();

    public abstract ActivityGroupDAO getActivityGroupDAO();

    public abstract PracticalActionFormsDAO getPracticalActionFormsDAO();
}
