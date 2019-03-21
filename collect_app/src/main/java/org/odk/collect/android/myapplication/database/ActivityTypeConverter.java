package org.odk.collect.android.myapplication.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;

import java.lang.reflect.Type;
import java.util.List;

public class ActivityTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Activity> toSiteMetaAttribute(String json) {
        Type type = new TypeToken<List<Activity>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String toString(List<Activity> activityList) {
        return gson.toJson(activityList);
    }
}
