package org.odk.collect.android.myapplication.sync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.odk.collect.android.application.Collect;

class InternetUtil {


    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Collect.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkConnectedToNetwork() {
        return isNetworkAvailable();
    }
}
