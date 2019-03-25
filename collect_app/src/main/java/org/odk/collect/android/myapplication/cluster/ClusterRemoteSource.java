package org.odk.collect.android.myapplication.cluster;

import org.odk.collect.android.myapplication.activity.ActivityLocalSource;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupLocalSouce;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.api.ServiceGenerator;

import io.reactivex.Observable;

public class ClusterRemoteSource {

    private static ClusterRemoteSource INSTANCE = null;

    public static ClusterRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClusterRemoteSource();
        }
        return INSTANCE;
    }

    public Observable<Object> getAll() {
        return ServiceGenerator.createService(ClusterAPI.class)
                .getCluster()
                .map(ducks -> {

                    for (Cluster d : ducks) {
                        ActivityGroupLocalSouce.getINSTANCE().save(d.getClusterag());
                        for (ActivityGroup activityGroup : d.getClusterag()) {
                            for (Activity activity : activityGroup.getActivity()) {
                                activity.setActivityGroupId(activityGroup.getId());
                                ActivityLocalSource.getInstance().save(activity);
                            }


                        }
                    }

                    return ducks;
                });
    }

}
