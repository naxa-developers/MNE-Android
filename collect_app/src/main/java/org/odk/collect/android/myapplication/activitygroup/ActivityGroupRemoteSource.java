package org.odk.collect.android.myapplication.activitygroup;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.activitygroup.model.ClusterResponse;
import org.odk.collect.android.myapplication.api.ServiceGenerator;
import org.odk.collect.android.myapplication.common.TitleDesc;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
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


    public Observable<List<ClusterResponse>> getAll() {
        return ServiceGenerator.createService(ActivityGroupAPI.class)
                .getCluster()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }


    public Observable<ArrayList<TitleDesc>> getAllActivitiesGroup() {
        return ServiceGenerator.createService(ActivityGroupAPI.class)
                .getCluster()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<List<ClusterResponse>, ArrayList<TitleDesc>>() {
                    @Override
                    public ArrayList<TitleDesc> apply(List<ClusterResponse> clusterResponses) throws Exception {
                        ArrayList<TitleDesc> titleDescs = new ArrayList<>();
                        TitleDesc titleDesc;
                        for (ClusterResponse clusterResponse : clusterResponses) {
                            for (ActivityGroup activitygroup : clusterResponse.getActivitygroup()) {
                                String clusterId = clusterResponse.getId();
                                String name = activitygroup.getName();
                                String desc = activitygroup.getDescription();
                                titleDesc = new TitleDesc(name, desc, "", clusterId);
                                titleDescs.add(titleDesc);
                            }
                        }

                        return titleDescs;
                    }
                });

    }


    public Observable<ArrayList<TitleDesc>> getAllActivity(String activityGroupId) {
        return ServiceGenerator.createService(ActivityGroupAPI.class)
                .getCluster()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).map(new Function<List<ClusterResponse>, ArrayList<TitleDesc>>() {
                    @Override
                    public ArrayList<TitleDesc> apply(List<ClusterResponse> clusterResponses) throws Exception {
                        ArrayList<TitleDesc> titleDescs = new ArrayList<>();
                        TitleDesc titleDesc;
                        for (ClusterResponse clusterResponse : clusterResponses) {
                            for (ActivityGroup activitygroup : clusterResponse.getActivitygroup()) {
                                for (Activity activity : activitygroup.getActivity()) {
                                    String name = activity.getName();
                                    String desc = activity.getDescription();
                                    titleDesc = new TitleDesc(name, desc, activity.getForm(), "");
                                    titleDescs.add(titleDesc);
                                }
                            }
                        }

                        return titleDescs;
                    }
                });

    }


    public Observable<Object> getActivityGroups() {
        return ServiceGenerator.createService(ActivityGroupAPI.class)
                .getActivityGroup()
                .flatMap(new Function<List<ActivityGroup>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<ActivityGroup> activityGroups) throws Exception {
                        return ActivityGroupLocalSouce.getINSTANCE().save(activityGroups).toObservable();
                    }
                });
    }

}
