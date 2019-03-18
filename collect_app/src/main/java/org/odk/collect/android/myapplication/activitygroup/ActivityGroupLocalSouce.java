package org.odk.collect.android.myapplication.activitygroup;

public class ActivityGroupLocalSouce {
    private static ActivityGroupLocalSouce INSTANCE = null;


    public static ActivityGroupLocalSouce getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityGroupLocalSouce();
        }
        return INSTANCE;
    }


}
