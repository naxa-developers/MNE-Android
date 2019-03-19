package org.odk.collect.android.myapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


import org.odk.collect.android.myapplication.PracticalActionSplashScreenActivity;

import java.util.HashMap;

public class ActivityUtil {

    public static void openActivity(Class className, AppCompatActivity context, HashMap<String, ?> data, boolean skipAnimation) {
        Intent intent = new Intent(context, className);

        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (skipAnimation) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }


        if (EmptyUtil.isNotNull(data) && EmptyUtil.isNotEmpty(data)) {
            intent.putExtra("map", data);
        }

        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void openSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, PracticalActionSplashScreenActivity.REQUEST_CODE_SETTINGS);
    }
}
