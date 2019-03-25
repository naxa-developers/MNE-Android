package org.odk.collect.android.myapplication.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.PracticalActionSplashScreenActivity;
import org.odk.collect.android.provider.FormsProviderAPI;

import java.util.HashMap;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class ActivityUtil {

    public static class KEYS {
        public static String ACTIVITY_ID = "activity_id";
        public static String BENEFICIARY_ID = "beneficiary_id";
        public static String FORM_ID = "form_id";
    }

    public static void openActivity(Class className, Context context, HashMap<String, ?> data, boolean skipAnimation) {
        Intent intent = new Intent(context, className);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (skipAnimation) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }


        if (EmptyUtil.isNotNull(data) && EmptyUtil.isNotEmpty(data)) {
            intent.putExtra("map", data);
        }

        context.startActivity(intent);
    }

    public static void openSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, PracticalActionSplashScreenActivity.REQUEST_CODE_SETTINGS);
    }

    public static void openFormEntryActivity(Context activity, String idString, String activityId, String beneficiaryId) throws CursorIndexOutOfBoundsException, NullPointerException, NumberFormatException {


        try {
            long formId = getFormId(idString);
            Uri formUri = ContentUris.withAppendedId(FormsProviderAPI.FormsColumns.CONTENT_URI, formId);
            Intent toFormEntry = new Intent(Intent.ACTION_EDIT, formUri);
            toFormEntry.putExtra(KEYS.ACTIVITY_ID, activityId);
            toFormEntry.putExtra(KEYS.BENEFICIARY_ID, beneficiaryId);
            toFormEntry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            toFormEntry.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(toFormEntry);
        } catch (CursorIndexOutOfBoundsException e) {

            Timber.e("Failed to load xml form  %s", e.getMessage());
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            Timber.e("Failed to load xml form %s", e.getMessage());
        }


    }


    private static long getFormId(String jrFormId) throws CursorIndexOutOfBoundsException, NullPointerException, NumberFormatException {

        String[] projection = new String[]{FormsProviderAPI.FormsColumns._ID, FormsProviderAPI.FormsColumns.FORM_FILE_PATH};
        String selection = FormsProviderAPI.FormsColumns.JR_FORM_ID + "=? ";
        String[] selectionArgs = new String[]{jrFormId};
        String sortOrder = FormsProviderAPI.FormsColumns._ID + " DESC LIMIT 1";

        Cursor cursor = Collect.getInstance().getContentResolver().query(FormsProviderAPI.FormsColumns.CONTENT_URI,
                projection,
                selection, selectionArgs, sortOrder);

        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(FormsProviderAPI.FormsColumns._ID);
        long formId = Long.parseLong(cursor.getString(columnIndex));

        cursor.close();

        return formId;
    }
}
