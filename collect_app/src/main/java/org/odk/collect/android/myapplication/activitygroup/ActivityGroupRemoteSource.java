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
                .flatMap((Function<List<Activity>, ObservableSource<List<Activity>>>) activityList -> {
                    for (Activity activity : activityList) {
                        activity.setActivityGroupId(id);
                    }
                    return ActivityLocalSource.getInstance().saveCompletable(activityList).toObservable();
                });
//                .map(new Function<List<ActivityGroup>, List<ActivityGroup>>() {
//                    @Override
//                    public List<ActivityGroup> apply(List<ActivityGroup> activityGroups) throws Exception {
//                        ActivityGroupLocalSouce.getINSTANCE().save(activityGroups);
//
//                        for (ActivityGroup activityGroup : activityGroups) {
//                            for (Activity activity : activityGroup.getActivity()) {
//                                activity.setActivityGroupId(activityGroup.getId());
//                            }
//                            ActivityLocalSource.getInstance().save(activityGroup.getActivity());
//                        }
//
//                        return activityGroups;
//                    }
//                });
    }

}
