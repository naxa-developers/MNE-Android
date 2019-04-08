package org.odk.collect.android.myapplication.preferences;

import android.content.Context;
import android.database.Cursor;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.FormsDao;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.listeners.DeleteFormsListener;
import org.odk.collect.android.listeners.DeleteInstancesListener;
import org.odk.collect.android.myapplication.api.ServiceGenerator;
import org.odk.collect.android.myapplication.database.PracticalActionDatabase;
import org.odk.collect.android.myapplication.utils.SharedPreferenceUtils;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.provider.InstanceProviderAPI;
import org.odk.collect.android.tasks.DeleteFormsTask;
import org.odk.collect.android.tasks.DeleteInstancesTask;

import java.util.ArrayList;

import timber.log.Timber;

class PraticalActionUserSession {
    public static PraticalActionUserSession getInstance() {
        return new PraticalActionUserSession();
    }

    private PraticalActionUserSession() {
    }

    public void logout(DeleteFormsListener listener) {

        ServiceGenerator.clear();
        PracticalActionDatabase.getDatabase(Collect.getInstance()).clearAllTables();
        SharedPreferenceUtils.deleteAll(Collect.getInstance());
        removeFormsAndInstances(Collect.getInstance(), listener);

    }


    private static void removeFormsAndInstances(Context context, DeleteFormsListener listener) {
        DeleteInstancesTask deleteInstancesTask = new DeleteInstancesTask();
        deleteInstancesTask.setContentResolver(context.getContentResolver());
        deleteInstancesTask.setDeleteListener(new DeleteInstancesListener() {
            @Override
            public void deleteComplete(int deletedInstances) {
                DeleteFormsTask deleteFormsTask = new DeleteFormsTask();
                deleteFormsTask.setContentResolver(context.getContentResolver());
                deleteFormsTask.setDeleteListener(listener);
                deleteFormsTask.execute(getAllFormsIds());
            }

            @Override
            public void progressUpdate(int progress, int total) {
                Timber.i("Deleting %s out of %s instances", progress, total);
            }
        });


        deleteInstancesTask.execute(getAllInstancedsIds());

    }

    private static Long[] getAllFormsIds() {
        Cursor results = null;
        ArrayList<Long> ids = new ArrayList<>();
        try {
            results = new FormsDao().getFormsCursor();
            if (results.getCount() > 0) {
                results.moveToPosition(-1);
                while (results.moveToNext()) {
                    String id = results.getString(results
                            .getColumnIndex(FormsProviderAPI.FormsColumns._ID));

                    ids.add(Long.valueOf(id));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();//should never happen
        } finally {
            if (results != null) {
                results.close();
            }
        }

        Long[] longs = new Long[ids.size()];
        return ids.toArray(longs);
    }

    private static Long[] getAllInstancedsIds() {
        Cursor results = null;
        ArrayList<Long> ids = new ArrayList<>();
        try {
            results = new InstancesDao().getInstancesCursor(null, null, null, null);
            if (results.getCount() > 0) {
                results.moveToPosition(-1);
                while (results.moveToNext()) {
                    String id = results.getString(results
                            .getColumnIndex(InstanceProviderAPI.InstanceColumns._ID));

                    ids.add(Long.valueOf(id));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();//should never happen
        } finally {
            if (results != null) {
                results.close();
            }
        }

        Long[] longs = new Long[ids.size()];
        return ids.toArray(longs);
    }
}
