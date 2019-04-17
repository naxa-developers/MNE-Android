package org.odk.collect.android.myapplication.activitygroup;

import org.odk.collect.android.myapplication.activity.ActivityLocalSource;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.api.ServiceGenerator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ActivityGroupRemoteSource {
    private static ActivityGroupRemoteSource INSTANCE = null;

    public static ActivityGroupRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityGroupRemoteSource();
        }
        return INSTANCE;
    }


    public Observable<List<Activity>> getActivityGroup(String id) {
        return ServiceGenerator.createService(ActivityGroupAPI.class)
                .getActivityGroup(id)
                .subscribeOn(Schedulers.io())
                .map(activityList -> {
                    for (Activity activity : activityList) {
                        activity.setActivityGroupId(id);
                    }
                    ActivityLocalSource.getInstance().save(activityList);

                    return activityList;
                });
    }


}
