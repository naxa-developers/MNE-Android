package org.odk.collect.android.myapplication.cluster;

import org.odk.collect.android.myapplication.activitygroup.ActivityGroupLocalSouce;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupRemoteSource;
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
                .map(new Function<List<Cluster>, List<Cluster>>() {
                    @Override
                    public List<Cluster> apply(List<Cluster> clusters) throws Exception {
                        ActivityGroupLocalSouce.getINSTANCE().deleteAll();
                        ClusterLocalSource.getInstance().deleteAll();
                        return clusters;
                    }
                })
                .flatMapIterable((Function<List<Cluster>, Iterable<Cluster>>) clusters -> {
                    ClusterLocalSource.getInstance().save(clusters);
                    return clusters;
                })
                .map(cluster -> {
                    for (ActivityGroup activityGroup : cluster.getClusterag()) {
                        activityGroup.setCluster(cluster.getId());
                    }
                    ActivityGroupLocalSouce.getINSTANCE().save(cluster.getClusterag());
                    return cluster.getClusterag();
                });

    }

}
