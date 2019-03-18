package org.odk.collect.android.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class ActivityUtil {

    public static void openActivity(Class className, Context context, HashMap<String, ?> data, boolean skipAnimation) {
        Intent intent = new Intent(context, className);

        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (skipAnimation) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }


        if (EmptyUtil.isNotNull(data) && EmptyUtil.isNotEmpty(data)) {
            intent.putExtra("map", data);
        }

        context.startActivity(intent);
    }

}
