package org.odk.collect.android.myapplication.activitygroup;

import org.odk.collect.android.myapplication.activitygroup.model.ClusterResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ActivityGroupAPI {

    @GET("/core/cluster")
    Observable<List<ClusterResponse>> getActivityGroup();

}
