package org.odk.collect.android.myapplication.common;

public class Constant {
   // public static final String BASE_URL = "http://192.168.88.41:8001";
    public static final String BASE_URL = "https://mne.naxa.com.np";
    public static final String FORM_LIST = BASE_URL + "/formsList/";

    public static class URLs {
        public static final String GET_CLUSTER = "/core/cluster";
        public static final String GET_ACT_GROUP = "/core/activitygroup/{id}";
    }

    public static class SERVICE {
        static String SERVICE_AUTHORITY = "org.odk.collect.android.myapplication.sync.DataSyncService";
        public static String STARTFOREGROUND_SYNC = SERVICE_AUTHORITY + "start";
        public static String STOPFOREGROUND_SYNC = SERVICE_AUTHORITY + "stop";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_DATA_SYNC_SERVICE = 101;
        int DATA_UPTO_DATE = 102;
        int DATA_SYNC_ERROR = 103;
        int ODK_FORM_SYNC = 104;
    }

}
