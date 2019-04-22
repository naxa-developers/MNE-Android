package org.odk.collect.android.myapplication.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class DateUtils {

    public static boolean hasEndDatePassed(String endate) {

        try {
            DateTime dateTime = DateTime.parse(endate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            return dateTime.isBeforeNow();
        } catch (Exception e) {
            return false;
        }
    }


    public static String formatDate(String endate) {
        try {
            DateTime dateTime = DateTime.parse(endate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            String formattedDate = dateTime.toString(DateTimeFormat.longDate());
            Timber.i("Formatted %s to %s", endate, formattedDate);
            return formattedDate;
        } catch (Exception e) {
            Timber.e(e);
            return endate;
        }
    }

}
