package org.odk.collect.android.myapplication.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtils {

    public static boolean hasEndDatePassed(String endate) {
        DateTime dateTime = DateTime.parse(endate, DateTimeFormat.forPattern("yyyy-mm-dd"));
        return dateTime.isBeforeNow();
    }
}
