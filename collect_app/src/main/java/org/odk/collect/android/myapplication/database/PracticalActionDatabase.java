package org.odk.collect.android.myapplication.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                        .build();
            }
        }

        return INSTANCE;
    }

}
