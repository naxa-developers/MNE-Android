package org.odk.collect.android.myapplication.cluster;

import org.odk.collect.android.myapplication.activitygroup.ActivityGroupLocalSouce;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.api.ServiceGenerator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class ClusterRemoteSource {

    private static ClusterRemoteSource INSTANCE = null;

    public static ClusterRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClusterRemoteSource();
        }
        return INSTANCE;
    }

    public Observable<List<ActivityGroup>> getAll() {
        return ServiceGenerator.createService(ClusterAPI.class)
                .getCluster()
                .flatMapIterable(new Function<List<Cluster>, Iterable<Cluster>>() {
                    @Override
                    public Iterable<Cluster> apply(List<Cluster> clusters) throws Exception {
                        return clusters;
                    }
                })
                .map(new Function<Cluster, List<ActivityGroup>>() {
                    @Override
                    public List<ActivityGroup> apply(Cluster cluster) throws Exception {
                        ActivityGroupLocalSouce.getINSTANCE().save(cluster.getClusterag());
                        return cluster.getClusterag();
                    }
                });

//                .map(new Function<List<Cluster>, List<ActivityGroup>>() {
//                    @Override
//                    public List<ActivityGroup> apply(List<Cluster> clusters) throws Exception {
//
//                        for (Cluster d : clusters) {
//                            ActivityGroupLocalSouce.getINSTANCE().save(d.getClusterag());
//                            for (ActivityGroup activityGroup : d.getClusterag()) {
//                                for (Activity activity : activityGroup.getActivity()) {
//                                    activity.setActivityGroupId(activityGroup.getId());
//                                    ActivityLocalSource.getInstance().save(activity);
//                                }
//                            }
//                        }
//
//                        return clusters.g
//                    }
//                });
    }

}
